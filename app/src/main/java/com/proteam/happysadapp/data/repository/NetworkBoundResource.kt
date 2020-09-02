package com.tribe.fitness.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tribe.fitness.data.retrofit.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This class has been designed in keeping single source of truth principal in mind. In an ideal application data onto view should be provided from single source,
 * But most of the time there are multiple data provider for single view. for exa - Database, Remote Source etc. So to achieve single source of truth concept this
 * class has been designed. It fetches data from remote source saves in database and then databse response gets propagated to view. Though we have added few helper
 * methods in case dev doesn't want to save data locally or they only want to fetch data from local source.
 *
 *
 * Created by Mohd. Irfan on 12/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
abstract class NetworkBoundResource<LocalResponseType, RemoteResponseType>(var coroutineScope: CoroutineScope) {

    private var result: MediatorLiveData<ApiResponse>;
    private val apiResponse = ApiResponse();

    init {
        this.result = MediatorLiveData();
    }

    fun execute(): NetworkBoundResource<LocalResponseType, RemoteResponseType> {
        if (shouldFetchLocalData()) {
            val dbSource: LiveData<LocalResponseType>? = loadFromDb()
            if (dbSource == null) {
                result.postValue(apiResponse)
                if (shouldFetchFromRemote()) {
                    fetchFromRemoteProvider()
                }
            } else {
                result.addSource(dbSource) { response ->
                    result.removeSource(dbSource)
                    if (response == null && !shouldFetchFromRemote()) {
                        result.postValue(apiResponse)
                    } else if (response != null) {
                        if (shouldFetchFromRemote()) {
                            apiResponse.enableApiHit();
                        }
                        apiResponse.setData(response)
                        result.postValue(apiResponse)
                    }
                    if (shouldFetchFromRemote()) {
                        fetchFromRemoteProvider()
                    }
                }
            }
        } else {
            fetchFromRemoteProvider()
        }
        return this
    }

    private fun fetchFromRemoteProvider() {
        coroutineScope.launch {
            val remoteSource: LiveData<ApiResponse> = fetchRemoteData()
            result.addSource(remoteSource, object : Observer<ApiResponse> {
                override fun onChanged(response: ApiResponse?) {
                    result.removeSource(remoteSource)
                    apiResponse.disableApiHit()
                    if (response == null) {
                        result.postValue(apiResponse)
                    } else if (response.getData() == null) {
                        apiResponse.setApiError(response.getApiError())
                        result.postValue(apiResponse)
                    } else {
                        apiResponse.setData(response.getData())
                        result.postValue(apiResponse)
                        coroutineScope.launch(Dispatchers.IO) {
                            saveResponseLocally(apiResponse)
                        }
                    }
                }
            })
        }
    }

    @WorkerThread
    abstract suspend fun saveResponseLocally(remoteResponse: ApiResponse)

    @MainThread
    abstract fun shouldFetchLocalData(): Boolean

    @MainThread
    abstract fun shouldFetchFromRemote(): Boolean

    @WorkerThread
    abstract fun loadFromDb(): LiveData<LocalResponseType>?

    @MainThread
    abstract suspend fun fetchRemoteData(): MutableLiveData<ApiResponse>

    fun asLiveData(): MediatorLiveData<ApiResponse> {
        return result;
    }
}

package com.tribe.fitness.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.tribe.fitness.data.retrofit.*
import com.google.gson.JsonSyntaxException
import com.proteam.happysadapp.BaseApplication
import com.proteam.happysadapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject


/**
 * Base class for network req/response. All classes which intract with remote data must extend it.
 * purpose of this class and it's child classes is to intract with remote data source.
 *
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
open class BaseApiHelperImpl @Inject constructor(var mRetrofit: Retrofit) : BaseApiHelper {

    private val ONE_MIN_IN_MILLISECONDS = 60000
    private lateinit var apiCall: Call<ApiResponse>


    /**
     * Gets api header.
     *
     * @param isWithAccessToken the is with access token
     * @return the api header
     */
    fun getApiHeader(isWithAccessToken: Boolean): HashMap<String, String> {
        val map = HashMap<String, String>()
//        map["content-language"] = contentLanguage;
//        map["utcoffset"] = getCurrentZoneOffset()
//        if (isWithAccessToken) {
//            map["authorization"] = mBaseDbHelper.getAccessToken()
//        }
        return map
    }

    fun getApiHeader(accessToken: String?): HashMap<String, String> {
        val map = HashMap<String, String>()
//        map["content-language"] = contentLanguage;
        map["utcoffset"] = getCurrentZoneOffset()
        if (!accessToken.isNullOrEmpty()) {
            map["access_token"] = accessToken
        }
        return map
    }

    /**
     * Gets api interface.
     *
     * @param isWithIncreasedTimeOut the is with increased time out
     * @return the api interface
     */
    fun getApiInterface(isWithIncreasedTimeOut: Boolean): ApiInterface? {
        return mRetrofit.create(ApiInterface::class.java)
    }


    /**
     * Execute api call.
     *
     * @param mApiCall     the m api call
     * @param mApiListener the m api listener
     */
    fun executeApiCall(mApiCall: Call<ApiResponse>, liveData: MutableLiveData<ApiResponse>) {
        this.apiCall = mApiCall
        mApiCall.enqueue(object : Callback<ApiResponse> {
            val apiResponse = ApiResponse()
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val mApiError = ApiError(0, getMessageFromThrowable(t))
                apiResponse.setApiError(mApiError)
                liveData.postValue(apiResponse)
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful()) {
                    if (liveData != null) {
                        liveData.postValue(response.body())
                    }
                } else {
                    apiResponse.setApiError(RetrofitUtils.parseError(mRetrofit, response)!!)
                    liveData.postValue(apiResponse)
                }
            }

        })
    }

    /**
     * Method to get current timezone offset value
     *
     * @return offset value in minutes
     */
    open fun getCurrentZoneOffset(): String {
        val tz = TimeZone.getDefault()
        val now = Date()
        return java.lang.String.valueOf(tz.getOffset(now.time) / ONE_MIN_IN_MILLISECONDS)
    }

    //for temp solution
    private fun getMessageFromThrowable(cause: Throwable?): String? {
        val mContext: Context = BaseApplication.getAppContext()
        return if (mContext == null) {
            cause!!.localizedMessage
        } else {
            if (cause is UnknownHostException) {
                return mContext.getString(R.string.error_internet_not_connected)
            } else if (cause is SocketTimeoutException) {
                return mContext.getString(R.string.error_internet_not_connected)
            } else if (cause is ConnectException) {
                return mContext.getString(R.string.error_internet_not_connected)
            } else if (cause is JsonSyntaxException) {
                return mContext.getString(R.string.error_paring)
            }
            mContext.getString(R.string.error_unexpected_error)
        }
    }
}
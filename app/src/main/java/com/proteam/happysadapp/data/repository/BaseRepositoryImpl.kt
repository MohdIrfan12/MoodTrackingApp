package com.tribe.fitness.repository

import androidx.lifecycle.LiveData
import com.tribe.fitness.data.BaseApiHelper
import com.tribe.fitness.data.db.AppDatabase
import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.data.local.UserMoodDao
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Base data provider class(Repository).All data provider must extend this class
 * --> The purpose of this class is to have a layer between viewmodel and data storage.
 * --> This class acts as data provider. It serves data either from local or remote storage depending upon the situation
 *      View model intaract with this class.
 * --> Devs don't need to create data provider class for each screen, they can sbclass
 *     it on module basis. so one data provider class for one entire module.
 * for exa - OnboardingRepositryImpl for onboarding module.
 *
 * -- In a clean architecture inner layer shouldn't depend on outer layer. That is why in this app.live data object will be declared in repository.
 *    view will just obeserve it via viewmodel
 */

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 *
 */
open class BaseRepositoryImpl<T : BaseApiHelper> : BaseRepository {

    private var mApiHelper: T
    private var job: Job
    open var mAppDatabase: AppDatabase

    @Inject
    constructor(mAppDatabase: AppDatabase, mApiHelper: T) {
        this.mAppDatabase = mAppDatabase;
        this.mApiHelper = mApiHelper;
        job = Job()
    }

    suspend fun getAccessToken(): String? {
//        val userData = async(Dispatchers.IO) {
//            getUserDataDao().getUserData()
//        }.await()
//        if (userData == null) {
//            return null
//        } else {
//            return userData!!.access_token
//        }
        return ""
    }

//    protected fun initApiHelper(apiHelper: T) {
//        mApiHelper = apiHelper;
//    }

    protected fun getApiHelper(): T {
        return mApiHelper;
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun getUserMoodDao(): UserMoodDao {
        return mAppDatabase.getUserMoodDao()
    }



    override fun onDestroy() {
        job.cancel()
    }
}
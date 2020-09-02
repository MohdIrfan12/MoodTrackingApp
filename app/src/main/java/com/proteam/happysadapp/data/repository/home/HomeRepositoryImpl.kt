package com.tribe.fitness.repository.onboarding.companiondevice

import com.tribe.fitness.data.BaseApiHelper
import com.tribe.fitness.data.db.AppDatabase
import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.repository.BaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * This class act as a interface between CompanioDevices Data providers and modlues which are accesing that data.
 *
 * Created by Mohd Irfan on 05/05/20.
 * email -  mohd.irfan@tribe.fitness
 */
class HomeRepositoryImpl @Inject constructor(
    mAppDatabase: AppDatabase,
    mApiHelper: BaseApiHelper
) :
    BaseRepositoryImpl<BaseApiHelper>(mAppDatabase, mApiHelper), HomeRepository {


    override fun saveData(status: String) {
        launch(Dispatchers.IO) {
            val calender = Calendar.getInstance()
            val userMood = UserMood(
                System.currentTimeMillis().toString(),
                Calendar.DAY_OF_MONTH,
                Calendar.WEEK_OF_MONTH,
                Calendar.MONTH,
                Calendar.YEAR,
                status
            );
            getUserMoodDao().insert(userMood)
        }
    }

    override suspend fun getAllData(status: String): List<UserMood> {
        return getUserMoodDao().getUserData(status)
    }
}
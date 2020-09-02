package com.tribe.fitness.repository.onboarding.companiondevice

import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.repository.BaseRepository

/** Wrapper on CompanionDevices repository.
 *
 * Created by Mohd Irfan on 05/05/20.
 * email -  mohd.irfan@tribe.fitness
 */
interface HomeRepository : BaseRepository {

    fun saveData(status: String)

    suspend fun getAllData(status: String):List<UserMood>
}
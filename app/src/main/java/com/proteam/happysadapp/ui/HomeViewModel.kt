package com.proteam.happysadapp.ui

import com.tribe.fitness.base.viewmodel.BaseViewModel
import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.repository.onboarding.companiondevice.HomeRepository

/**
 * Created by Mohd Irfan on 02/09/20.
 * email -  mohd.irfan@bambooapp.com
 */
class HomeViewModel(repository: HomeRepository) : BaseViewModel<HomeRepository>(repository) {

    fun saveData(status: String) {
        getRepository().saveData(status)
    }

    suspend fun getAllData(status: String): List<UserMood> {
       return getRepository().getAllData(status)
    }

}
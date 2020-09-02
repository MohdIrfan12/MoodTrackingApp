package com.tribe.fitness.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.proteam.happysadapp.base.CommonActions
import com.tribe.fitness.data.BaseLiveData
import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.repository.BaseRepository

/**
 * Base class for view models. All view models should extend it.
 *
 * Created by Mohd. Irfan on 09/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
open class BaseViewModel<T : BaseRepository> : ViewModel {


    protected val commonActionsLiveData: BaseLiveData<CommonActions>
    private val mRepository: T;
    private var userLiveMood: LiveData<UserMood>? = null

    constructor(repository: T) : super() {
        this.mRepository = repository;
        commonActionsLiveData = BaseLiveData();
        commonActionsLiveData.setDataWithoutObserve(CommonActions())
    }

    fun getCommonActionLiveData() = commonActionsLiveData;


    protected fun getRepository(): T {
        return mRepository;
    }

    override fun onCleared() {
        mRepository.onDestroy()
        super.onCleared()
    }
}
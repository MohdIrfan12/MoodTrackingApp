package com.tribe.fitness.data

import androidx.lifecycle.LiveData


/**
 * Base class for live data's.
 * In mobile application development most of the time we update value in data container
 * without notifying views about it. This class has specially been designed for covering those scenarios.
 * All live data in application must extend it.
 *
 */

/**
 * created by Mohd. Irfan on 09/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 *
 * @param T
 */
open class BaseLiveData<T>() : LiveData<T>() {
    private var data: T? = null


    open fun setData(value: T) {
        this.data = value;
        postValue(data)
    }

    open fun setDataWithoutObserve(value: T) {
        this.data = value;
    }

    open fun getData() = data;
}
package com.tribe.fitness.base.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * This interface has been designed to perform some common operations with fragment or activity
 *  helper methods for those task has beend defined here.
 *
 * Created by Mohd. Irfan on 09/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
interface BaseView {

    /**
     * for inflating xml layout
     *
     * @return xml layout id
     */
    fun getLayout(): Int


    /**
     * perform component initialisation here,
     * such as view model initialisation
     *
     */
    fun attachComponent()

    /**
     * perform component de-initialisation here.
     * such as marking component nul
     */
    fun detachComponent()

    /**
     * for showing full scree loader.
     *
     * @param msg message string
     * @param message string.xml id of message
     */
    fun showLoader(msg: String = "", @StringRes message: Int = 0)

    fun showLoader(@DrawableRes drawableId: Int)
    /**
     * to hidel funn screen loader
     *
     */
    fun hideLoader()

    /**
     * call this method for showing error messages
     *
     * @param msg message string
     * @param message string.xml id of message
     */
    fun displayError(msg: String = "", @StringRes message: Int = 0)

    fun displayErrorWithRetry(msg: String = "", onErrorCallback: OnErrorCallback)

    fun displayErrorWithRetry(@StringRes message: Int = 0, onErrorCallback: OnErrorCallback)

    /**
     * call this method for showing toast on screen
     *
     * @param msg message string
     * @param message string.xml id of message
     */
    fun showToast(msg: String = "", @StringRes message: Int = 0)

    /**
     * for hiding keyboard on particular event
     *
     */
    fun hideKeyboard();

    fun checkNetworkConnectedAndShowError(): Boolean

    fun isNetworkConnected(): Boolean

    interface OnErrorCallback {
        fun onErrorClick()
    }

}
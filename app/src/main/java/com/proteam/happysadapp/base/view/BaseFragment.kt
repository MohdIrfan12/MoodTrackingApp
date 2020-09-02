package com.tribe.fitness.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.proteam.happysadapp.base.CommonActions
import com.tribe.fitness.base.viewmodel.BaseViewModel


/**
 * This fragment should be the parent class for all fragments inside project.
 * It has been designed to help focus on designated task instead of some boilerplate code.
 * All fragments inside applciation must extend it.
 *
 * Created by Mohd. Irfan on 09/04/20.
 * Email -  mohd.irfan@tribe.fitness
 */


abstract class BaseFragment<VM : BaseViewModel<*>> : Fragment(), BaseView,
    FragmentToActivityInteractionListener {

    protected var viewModel: VM? = null
    private lateinit var mFragmentToActivityInteractionListener: FragmentToActivityInteractionListener


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (getContext() is FragmentToActivityInteractionListener) {
            mFragmentToActivityInteractionListener =
                context as FragmentToActivityInteractionListener
        } else {
            if (getContext() != null) {
                throw RuntimeException(
                    this.javaClass.getSimpleName()
                            + " must implement FragmentToActivityInteractionListener"
                )
            }
        }
        attachComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    protected fun attachCommonObserver() {
        viewModel?.getCommonActionLiveData()?.observe(this,
            Observer<CommonActions> { commonActions ->
                if (commonActions == null) {
                    return@Observer
                }
                if (commonActions.action == CommonActions.SHOW_LOADER) {
                    showLoader()
                } else if (commonActions.action == CommonActions.HIDE_LOADER) {
                    hideLoader()
                }
            })
    }


    abstract fun initView(view: View, savedInstanceState: Bundle?);

    open fun isHideKeyBoardOnOutSideTouch(): Boolean {
        return false
    }

    /**
     * Is back press allowed from fragment boolean.
     *
     * @return the boolean
     */
    open fun isBackPressAllowedFromFragment(): Boolean {
        return true
    }


    override fun showLoader(msg: String, message: Int) {
        mFragmentToActivityInteractionListener.showLoader(msg, message)
    }

    override fun showLoader(drawableId: Int) {
        mFragmentToActivityInteractionListener.showLoader(drawableId)
    }

    override fun hideLoader() {
        mFragmentToActivityInteractionListener.hideLoader()
    }

    override fun showToast(msg: String, message: Int) {
        mFragmentToActivityInteractionListener.showToast(msg, message)
    }

    override fun displayError(msg: String, message: Int) {
        mFragmentToActivityInteractionListener.displayError(msg, message)
    }

    override fun displayErrorWithRetry(msg: String, onErrorCallback: BaseView.OnErrorCallback) {
        mFragmentToActivityInteractionListener.displayErrorWithRetry(msg, onErrorCallback)
    }

    override fun displayErrorWithRetry(message: Int, onErrorCallback: BaseView.OnErrorCallback) {
        mFragmentToActivityInteractionListener.displayErrorWithRetry(message, onErrorCallback)
    }

    override fun hideKeyboard() {
        mFragmentToActivityInteractionListener.hideKeyboard()
    }

    override fun checkNetworkConnectedAndShowError(): Boolean {
        return mFragmentToActivityInteractionListener.checkNetworkConnectedAndShowError()
    }

    override fun isNetworkConnected(): Boolean {
        return mFragmentToActivityInteractionListener.isNetworkConnected()
    }


    override fun setResultOkAndCloseActivity() {
        mFragmentToActivityInteractionListener.setResultOkAndCloseActivity()
    }

    override fun attachComponent() {}

    override fun detachComponent() {}

    override fun onDetach() {
        detachComponent()
        super.onDetach()
    }
}
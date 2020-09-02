package com.tribe.fitness.base.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.proteam.happysadapp.R
import com.proteam.happysadapp.base.CommonActions
import com.proteam.happysadapp.base.view.ProgressDialog
import com.proteam.happysadapp.utill.AppLog
import com.tribe.fitness.base.viewmodel.BaseViewModel
import com.tribe.fitness.utill.DeviceUtil
import io.github.inflationx.viewpump.ViewPumpContextWrapper


/**
 * This activity should be considered parent class of all activities inside project.
 * It has been designed to help focus on designated task instead of few boilerplate code.
 * All activities inside application must extend it.
 *
 * Created by Mohd. Irfan on 09/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
abstract class BaseActivity<VM : BaseViewModel<*>> : FragmentActivity(), BaseView,
    FragmentToActivityInteractionListener {

    protected var viewModel: VM? = null
    private var mProgressDialog: Dialog? = null
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        attachComponent()
        initView(savedInstanceState)
    }


    override fun attachBaseContext(newBase: Context?) {
        var context = newBase?.let { ViewPumpContextWrapper.wrap(it) }
        super.attachBaseContext(context)
    }

    protected fun attachCommonObserber() {
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

    override fun showLoader(msg: String, message: Int) {
        hideLoader()
        mProgressDialog = ProgressDialog().show(this)
    }

    override fun showLoader(@DrawableRes drawableId: Int) {
        hideLoader()
        mProgressDialog = ProgressDialog().show(this, drawableId)
    }

    override fun hideLoader() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    @SuppressLint("ResourceType")
    override fun showToast(msg: String, message: Int) {
        if (TextUtils.isEmpty(msg) && message <= 0) {
            return
        }
        Toast.makeText(
            this, if (TextUtils.isEmpty(msg)) getString(message) else msg, Toast.LENGTH_SHORT
        ).show()
    }

    @SuppressLint("ResourceType")
    override fun displayError(msg: String, message: Int) {
        if (TextUtils.isEmpty(msg) && message <= 0) {
            return
        }
        snackbar = AppLog.showSnackBar(
            this,
            if (TextUtils.isEmpty(msg)) getString(message) else msg
        )
    }

    override fun displayErrorWithRetry(msg: String, onErrorCallback: BaseView.OnErrorCallback) {
        snackbar = AppLog.showSnackBar(this, msg, onErrorCallback)
    }

    override fun displayErrorWithRetry(message: Int, onErrorCallback: BaseView.OnErrorCallback) {
        snackbar = AppLog.showSnackBar(this, getString(message), onErrorCallback)
    }

    abstract fun initView(savedInstanceState: Bundle?);

    open fun isHideKeyBoardOnOutSideTouch(): Boolean {
        val currentFragment =
            if (supportFragmentManager.fragments.size > 0) supportFragmentManager.fragments.get(0) else null
        if (currentFragment == null || currentFragment !is BaseFragment<*>) {
            return false
        }
        return currentFragment.isHideKeyBoardOnOutSideTouch()
    }

    open fun isBackPressedAllowed(): Boolean {
        if (snackbar != null) {
            snackbar?.dismiss()
            snackbar = null
        }
        val currentFragment =
            if (supportFragmentManager.fragments.size > 0) supportFragmentManager.fragments.get(0) else null
        if (currentFragment == null || currentFragment !is BaseFragment<*>) {
            return true
        }
        return currentFragment.isBackPressAllowedFromFragment()
    }

    override fun attachComponent() {}

    override fun detachComponent() {}

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return if (isHideKeyBoardOnOutSideTouch()) {
            val view = currentFocus
            try {
                val ret = super.dispatchTouchEvent(ev)
                if (view is EditText) {
                    val w = currentFocus
                    val scrcoords = IntArray(2)
                    assert(w != null)
                    w!!.getLocationOnScreen(scrcoords)
                    val x = ev.rawX + w.left - scrcoords[0]
                    val y = ev.rawY + w.top - scrcoords[1]
                    if (ev.action == MotionEvent.ACTION_UP && (x < w.left || x >= w.right || y < w.top || y > w.bottom)) {
                        hideKeyboard()
                    }
                }
                return ret
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        } else {
            super.dispatchTouchEvent(ev)
        }
    }


    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null && window.currentFocus != null) {
            imm.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
        }
    }

    override fun checkNetworkConnectedAndShowError(): Boolean {
        return if (isNetworkConnected()) {
            true
        } else {
            displayError(getString(R.string.error_internet_not_connected))
            false
        }
    }

    override fun isNetworkConnected(): Boolean {
        return DeviceUtil.isNetworkAvailable(this)
    }

    override fun setResultOkAndCloseActivity() {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDestroy() {
        detachComponent()
        super.onDestroy()
    }
}
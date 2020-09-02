package com.proteam.happysadapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.proteam.happysadapp.BaseApplication
import com.proteam.happysadapp.R
import com.proteam.happysadapp.data.constant.MoodStatusConstant
import com.proteam.happysadapp.ui.stats.StatsActivity
import com.tribe.fitness.base.view.BaseActivity
import com.tribe.fitness.base.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class HomeActivity : BaseActivity<HomeViewModel>() {

    private val DELAY = 2 * 1000;
    @Inject
    open lateinit var viewModelFactory: ViewModelProviderFactory

    override fun getLayout(): Int {
        return R.layout.activity_home
    }

    override fun attachComponent() {
        super.attachComponent()
        BaseApplication.getAppComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        makeFullScreen()
        ivHappy.setOnClickListener { onClickHappy() }
        ivSad.setOnClickListener { onClickSad() }
        ivViewStats.setOnClickListener { startActivity(Intent(this, StatsActivity::class.java)) }
    }

    private fun makeFullScreen() {
        rootView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun onClickHappy() {
        showLoader(R.drawable.progress_happy);
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.saveData(MoodStatusConstant.HAPPY)
            delay(DELAY.toLong())
            withContext(Dispatchers.Main)
            {
                hideLoader()
                makeFullScreen()
            }
        }
    }

    private fun onClickSad() {
        showLoader(R.drawable.progress_sad);
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel?.saveData(MoodStatusConstant.SAD)
            delay(DELAY.toLong())
            withContext(Dispatchers.Main)
            {
                hideLoader()
                makeFullScreen()
            }
        }
    }
}

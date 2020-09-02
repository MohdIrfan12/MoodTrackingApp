package com.proteam.happysadapp

import android.app.Application
import android.content.Context
import com.tribe.fitness.coach.di.application.AppComponent
import com.tribe.fitness.coach.di.application.AppModule
import com.tribe.fitness.coach.di.application.DaggerAppComponent
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import java.lang.ref.WeakReference


/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */

class BaseApplication : Application() {
    private val TAG: String = BaseApplication::class.java.getSimpleName()

    companion object {
        private lateinit var mWeakReference: WeakReference<Context>;
        private const val REGULAR_FONT_PATH = "font/Avenir-Heavy.ttf"
        private lateinit var appComponent: AppComponent;

        /**
         * Getter to access Singleton instance
         *
         * @return instance of BaseApplication
         */
        fun getAppContext(): Context {
            return mWeakReference.get()!!
        }

        fun getAppComponent(): AppComponent {
            return appComponent;
        }
    }

    override fun onCreate() {
        initCalligraphy()
        super.onCreate()
        mWeakReference = WeakReference(this)
        initDagger()
    }

    private fun initCalligraphy() {
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath(REGULAR_FONT_PATH)
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                )
            ).build()
        )
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
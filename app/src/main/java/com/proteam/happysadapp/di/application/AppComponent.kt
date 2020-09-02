package com.tribe.fitness.coach.di.application



import com.proteam.happysadapp.ui.HomeActivity
import com.proteam.happysadapp.di.application.ViewModelModule
import com.proteam.happysadapp.ui.stats.StatsActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Mohd Irfan on 23/08/20.
 * email -  mohd.irfan@bambooapp.com
 */
@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: HomeActivity)

    fun inject(activity: StatsActivity)
}
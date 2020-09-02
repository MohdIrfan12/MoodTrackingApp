package com.tribe.fitness.coach.di.application

import android.app.Application
import androidx.annotation.UiThread
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.proteam.happysadapp.BuildConfig
import com.tribe.fitness.data.BaseApiHelperImpl
import com.tribe.fitness.data.db.AppDatabase
import com.tribe.fitness.data.retrofit.RetrofitUtils
import com.tribe.fitness.repository.BaseRepository
import com.tribe.fitness.repository.BaseRepositoryImpl
import com.tribe.fitness.repository.onboarding.companiondevice.HomeRepository
import com.tribe.fitness.repository.onboarding.companiondevice.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by Mohd Irfan on 23/08/20.
 * email -  mohd.irfan@bambooapp.com
 */
@UiThread
@Module
class AppModule(var mApplication: Application) {

    /**
     * This method must and only be called from Application class of module
     * creating singleton pattren for instatiating room database because db creation is a heavy process.
     * @param context application context to avoid memory leaks
     * @return Instance of this class
     */
    @Singleton
    @Provides
    fun getDatabase(): AppDatabase {
        return Room.databaseBuilder(mApplication!!, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(RetrofitUtils.httpClient(true, false).build())
            .build()
    }

    @Provides
    fun getBaseApiHelper(): BaseApiHelperImpl {
        return BaseApiHelperImpl(getRetrofit())
    }



    @Provides
    fun getBaseRepository(): BaseRepository {
        return BaseRepositoryImpl(getDatabase(), getBaseApiHelper())
    }

    @Provides
    fun getHomeRepository(): HomeRepository {
        return HomeRepositoryImpl(getDatabase(), getBaseApiHelper())
    }
}
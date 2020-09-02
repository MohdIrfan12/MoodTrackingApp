package com.tribe.fitness.utill

import android.content.Context
import android.net.ConnectivityManager
import java.util.*

object DeviceUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}
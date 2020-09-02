package com.proteam.happysadapp.base

import androidx.annotation.MainThread

/**
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
@MainThread
class CommonActions(var action: Int = 0) {

    companion object {
        const val SHOW_LOADER = 1;
        const val HIDE_LOADER = 2;
    }
}
package com.tribe.fitness.data.db

import com.tribe.fitness.data.local.UserMoodDao


/**
 * A wrapper class on AppDatabase. Define getter methods for DaO classes here.
 *  repository class must extend it. In tribe BaseRepositoryImpl is extending this file so don't worry about it.
 *
 *
 * Created by Mohd. Irfan on 13/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
interface BaseDbHelper {

    fun getUserMoodDao(): UserMoodDao;
}
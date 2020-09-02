package com.tribe.fitness.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tribe.fitness.data.db.AppDatabase.Companion.DB_VERSION
import com.tribe.fitness.data.entity.UserMood
import com.tribe.fitness.data.local.UserMoodDao

/**
 * Databse file. It has been desgined to persist required data locally
 *
 * Created by Mohd. Irfan on 12/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
@Database(
    entities = arrayOf(
        UserMood::class
    ), version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase(), BaseDbHelper {
    companion object {

        /**
         * define table name's here
         */
        const val TABLE_USER_MOOD = "user_mood"
        const val DB_NAME = "appDatabase.db"
        const val DB_VERSION = 1
    }

    override abstract fun getUserMoodDao(): UserMoodDao;
}
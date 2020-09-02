package com.tribe.fitness.repository

import androidx.lifecycle.LiveData
import com.tribe.fitness.data.BaseApiHelper
import com.tribe.fitness.data.db.BaseDbHelper
import com.tribe.fitness.data.entity.UserMood
import kotlinx.coroutines.CoroutineScope


/**
 * This class has been designed to provide an abstraction layer over data provider(Repository)
 *
 * Created by Mohd. Irfan on 10/04/20.
 * Organisation -  Tribe Inc.
 * Email -  mohd.irfan@tribe.fitness
 */
interface BaseRepository : BaseDbHelper, BaseApiHelper, CoroutineScope {
     fun onDestroy()
}
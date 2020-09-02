package com.tribe.fitness.data.local


import androidx.room.*
import com.tribe.fitness.data.db.AppDatabase
import com.tribe.fitness.data.entity.UserMood

@Dao
interface UserMoodDao {

    //    @Query("SELECT * FROM " + AppDatabase.TABLE_USER_MOOD + " LIMIT 1")
//    fun findUser(): LiveData<UserMood>
//
    @Query(
        "SELECT * FROM " + AppDatabase.TABLE_USER_MOOD + " WHERE status LIKE :status"
    )
    suspend fun getUserData(status: String): List<UserMood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userMood: UserMood)

    @Update
    fun update(userMood: UserMood): Int

    @Query("DELETE FROM " + AppDatabase.TABLE_USER_MOOD)
    fun delete()
}
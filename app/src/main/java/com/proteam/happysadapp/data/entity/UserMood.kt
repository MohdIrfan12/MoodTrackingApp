package com.tribe.fitness.data.entity

import android.os.Parcelable
import androidx.room.*
import com.tribe.fitness.data.db.AppDatabase
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = AppDatabase.TABLE_USER_MOOD)
data class UserMood(

    @PrimaryKey
    @SerializedName("timeStamp") var timeStamp: String,

    @ColumnInfo(defaultValue = "0")
    @SerializedName("day") var day: Int,

    @ColumnInfo(defaultValue = "0")
    @SerializedName("weak") var weak: Int?,

    @ColumnInfo(defaultValue = "0")
    @SerializedName("month") var month: Int?,

    @ColumnInfo(defaultValue = "0")
    @SerializedName("year") var year: Int?,

    @ColumnInfo(defaultValue = "NULL")
    @SerializedName("status") var status: String?
) : Parcelable {

}
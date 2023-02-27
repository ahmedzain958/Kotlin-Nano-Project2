package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "picture_of_the_day_table")
@Parcelize
data class PictureOfTheDayEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,                           // Created to use as the primary key
        val url: String,                             // primary key can't be a String
        val mediaType: String,
        val title: String
) : Parcelable

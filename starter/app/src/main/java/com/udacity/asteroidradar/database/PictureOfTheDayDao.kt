package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PictureOfTheDayDao {
    @Query("select * from picture_of_the_day_table")
    fun getPictureOfTheDay(): LiveData<PictureOfTheDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pictureOfTheDayEntity: PictureOfTheDayEntity)
}
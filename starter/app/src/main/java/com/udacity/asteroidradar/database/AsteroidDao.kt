package com.udacity.asteroidradar.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface AsteroidDao {
    @Query("select * from DatabaseAsteroid where closeApproachDate = :today")
    fun getAsteroids(today: String): LiveData<List<DatabaseAsteroid>>


    @Query("select * from DatabaseAsteroid")
    fun getSavedAsteroids(): LiveData<List<DatabaseAsteroid>>


    @Query("select * from DatabaseAsteroid where closeApproachDate between :startDate and :endDate order by closeApproachDate")
    fun getByFilterDate(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseAsteroid)
}
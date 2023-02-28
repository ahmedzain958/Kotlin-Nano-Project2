/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.udacity.asteroidradar.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.*
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfTheDay
import com.udacity.asteroidradar.getTodaysDate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.ArrayList

class AsteroidsRepository(private val database: AsteroidDatabase) {
    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(loadAsteroids()) {
            it.asDomainModel()
        }

    private fun loadAsteroids(): LiveData<List<DatabaseAsteroid>> {
        val asteroidsList = database.asteroidDao.getAsteroids(getTodaysDate())
      return asteroidsList
    }

    val pictureOfTheDay: LiveData<PictureOfTheDay> =
        Transformations.map(database.pictureOfTheDayDao.getPictureOfTheDay()) {
            it?.toDomainModel()
        }


    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        // Handle the exception here, for example:
        Log.e(TAG, "Coroutine Exception: $exception")
    }
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO + exceptionHandler) {
            val asteroids = Network.asteroids.getAsteroids().await()
            val parsedAsteroidsList: ArrayList<Asteroid> = parseAsteroidsJsonResult(JSONObject(asteroids))
            database.asteroidDao.insertAll(*parsedAsteroidsList.asDatabaseModel())
        }
    }


    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO + exceptionHandler) {
            try {
                val picture = Network.pictureOdTheDayService.getPictureOfTheDayAsync().await()
                database.pictureOfTheDayDao.insertAll(picture.toDatabaseModel())
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "PictureOfTheDay retrieval unsuccessful: ${e.message}")
                }
            }
        }
    }
}

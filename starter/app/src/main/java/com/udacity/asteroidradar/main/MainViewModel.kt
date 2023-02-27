package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)
    private val _asteroidList = asteroidsRepository.asteroids
    val asteroidList: LiveData<List<Asteroid>> = _asteroidList


    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

   /* init {
        val asteroid1 = Asteroid(
            1L,
            "Asteroid 1",
            "2023-03-01",
            4.5,
            12.3,
            15.4,
            0.01,
            false
        )
        val asteroid2 =  Asteroid(
            2L,
            "Asteroid 2",
            "2023-03-02",
            5.5,
            8.9,
            18.7,
            0.02,
            true
        )
        val asteroid3 = Asteroid(
            3L,
            "Asteroid 3",
            "2023-03-03",
            3.5,
            6.7,
            20.2,
            0.03,
            false
        )
        _asteroidList.value = listOf(asteroid1, asteroid2, asteroid3)
    }*/
   class Factory(val app: Application) : ViewModelProvider.Factory {
       override fun <T : ViewModel> create(modelClass: Class<T>): T {
           if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
               @Suppress("UNCHECKED_CAST")
               return MainViewModel(app) as T
           }
           throw IllegalArgumentException("Unable to construct viewmodel")
       }
   }
}
package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.Asteroid

class MainViewModel : ViewModel() {
    private val _asteroidList = MutableLiveData<List<Asteroid>>()
    val asteroidList: LiveData<List<Asteroid>> = _asteroidList

    init {
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
    }
}
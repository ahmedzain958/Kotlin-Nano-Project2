package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
enum class FILTER_TYPE { TODAY, WEEK, SAVE }

class MainViewModel( application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    val pictureOfTheDay = asteroidsRepository.pictureOfTheDay


    init {
        viewModelScope.launch {
            if (isOnline(application.applicationContext)) {
                asteroidsRepository.refreshAsteroids()
                asteroidsRepository.refreshPictureOfTheDay()
                asteroidsRepository.deleteOldAsteroids()
            }
        }
       /* viewModelScope.launch {
            if (isOnline(application.applicationContext)) {
            }
        }*/
    }

    val asteroidFilter = MutableLiveData<FILTER_TYPE>(FILTER_TYPE.TODAY)
    private val _asteroidList = Transformations.switchMap(asteroidFilter) {
        when (it) {
            FILTER_TYPE.TODAY -> asteroidsRepository.todayList
            FILTER_TYPE.WEEK -> asteroidsRepository.weekList
            else -> asteroidsRepository.asteroids
        }
    }
    val asteroidList: LiveData<List<Asteroid>> = _asteroidList
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                //It will check for both wifi and cellular network
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    fun updateFilter(filerView: FILTER_TYPE) {
        asteroidFilter.value = filerView
    }

}
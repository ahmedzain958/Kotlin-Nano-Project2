package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.domain.PictureOfTheDay
import com.udacity.asteroidradar.getDateAfterWeek
import com.udacity.asteroidradar.getTodaysDate
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// Configure retrofit to use the OkHttpClient

interface AsteroidsService {
    @GET("neo/rest/v1/feed?api_key=JMDo2pLHkMXDfkSr04w6ZMAgvvUHQeVGUviy5SvR")
    fun getAsteroids(
        @Query("start_date") start_date: String = getTodaysDate(),
        @Query("end_date") end_date: String = getDateAfterWeek(),
    ): Deferred<NeoFeed>

}

interface PictureOdTheDayService {
    // https://api.nasa.gov/planetary/apod?api_key=YOUR_API_KEY

    @GET("planetary/apod?api_key=JMDo2pLHkMXDfkSr04w6ZMAgvvUHQeVGUviy5SvR")
    fun getPictureOfTheDayAsync(): Deferred<PictureOfTheDay>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
//        .baseUrl("https://api.nasa.gov")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `Network.devbytes.getPlaylist()`
 */
object Network {


    val asteroids: AsteroidsService by lazy {
        retrofit.create(AsteroidsService::class.java)
    }

    val pictureOdTheDayService: PictureOdTheDayService by lazy {
        retrofit.create(PictureOdTheDayService::class.java)
    }
}

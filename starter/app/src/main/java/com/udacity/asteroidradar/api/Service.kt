package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface AsteroidsService {
    @GET("start_date=2020-02-07&end_date=2020-02-07&api_key=JMDo2pLHkMXDfkSr04w6ZMAgvvUHQeVGUviy5SvR")
    fun getAsteroids(): Deferred<NeoFeed>
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `Network.devbytes.getPlaylist()`
 */
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/neo/rest/v1/feed")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val devbytes = retrofit.create(AsteroidsService::class.java)
}

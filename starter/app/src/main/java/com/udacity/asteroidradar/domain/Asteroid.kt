package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long,
                    @Json(name = "name")
                    val name: String,
                    @Json(name = "close_approach_date")
                    val closeApproachDate: String,
                    @Json(name = "absolute_magnitude_h")
                    val absoluteMagnitude: Double,
                    @Json(name = "estimated_diameter_max")
                    val estimatedDiameter: Double,
                    @Json(name = "relative_velocity")
                    val relativeVelocity: Double,
                    @Json(name = "astronomical")
                    val distanceFromEarth: Double,
                    @Json(name = "is_potentially_hazardous_asteroid")
                    val isPotentiallyHazardous: Boolean) : Parcelable
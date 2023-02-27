package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.database.DatabaseAsteroid


data class EstimatedDiameter(
    val kilometers: Map<String, Double>,
    val meters: Map<String, Double>,
    val miles: Map<String, Double>,
    val feet: Map<String, Double>
)

data class RelativeVelocity(
    val kilometers_per_second: String,
    val kilometers_per_hour: String,
    val miles_per_hour: String
)

data class MissDistance(
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

data class CloseApproachData(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val relative_velocity: RelativeVelocity,
    val miss_distance: MissDistance,
    val orbiting_body: String
)

data class NearEarthObject(
    val links: Map<String, String>,
    val id: String,
    val neo_reference_id: String,
    val name: String,
    val nasa_jpl_url: String,
    val absolute_magnitude_h: Double,
    val estimated_diameter: EstimatedDiameter,
    val is_potentially_hazardous_asteroid: Boolean,
    val close_approach_data: List<CloseApproachData>,
    val is_sentry_object: Boolean
)

data class NearEarthObjects(
    val date: List<NearEarthObject>
)

data class NeoFeed(
    val near_earth_objects: Map<String, List<NearEarthObject>>
)
fun NeoFeed.asDomainModel(): List<Asteroid> {
    return near_earth_objects.flatMap { it.value }
        .map {
            Asteroid(
                id = it.id.toLong(),
                name = it.name,
                absoluteMagnitude = it.absolute_magnitude_h,
                estimatedDiameter = it.estimated_diameter.kilometers["estimated_diameter_min"] ?: 0.0,
                isPotentiallyHazardous = it.is_potentially_hazardous_asteroid,
                closeApproachDate = it.close_approach_data.firstOrNull()?.close_approach_date?:"",
                distanceFromEarth = it.close_approach_data.firstOrNull()?.miss_distance?.kilometers?.toDouble() ?: 0.0,
                relativeVelocity = it.close_approach_data.firstOrNull()?.relative_velocity?.kilometers_per_second?.toDouble() ?: 0.0
            )
        }
}


fun NeoFeed.asDatabaseModel(): Array<DatabaseAsteroid> {
    return near_earth_objects.flatMap { it.value }
        .map {
            DatabaseAsteroid(
                id = it.id.toLong(),
                name = it.name,
                absoluteMagnitude = it.absolute_magnitude_h,
                estimatedDiameter = it.estimated_diameter.kilometers["estimated_diameter_min"] ?: 0.0,
                isPotentiallyHazardous = it.is_potentially_hazardous_asteroid,
                closeApproachDate = it.close_approach_data.firstOrNull()?.close_approach_date?:"",
                distanceFromEarth = it.close_approach_data.firstOrNull()?.miss_distance?.kilometers?.toDouble() ?: 0.0,
                relativeVelocity = it.close_approach_data.firstOrNull()?.relative_velocity?.kilometers_per_second?.toDouble() ?: 0.0
            )
        }.toTypedArray()
}

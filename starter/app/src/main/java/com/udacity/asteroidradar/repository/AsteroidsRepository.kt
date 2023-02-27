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

import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.Asteroid
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository() {
    lateinit var  asteroids: MutableLiveData<List<Asteroid>>

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
             asteroids.value = Network.devbytes.getAsteroids().await().asDomainModel()
        }
    }
}

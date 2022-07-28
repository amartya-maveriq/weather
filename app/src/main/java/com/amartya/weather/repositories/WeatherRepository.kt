package com.amartya.weather.repositories

import android.location.Location
import com.amartya.weather.data.local.PlaceDao
import com.amartya.weather.data.remote.WeatherApiService
import com.amartya.weather.models.local.LocalPlace
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.await
import javax.inject.Inject


/**
 * Created by Amartya Ganguly on 27/06/22.
 */

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    private val placeDao: PlaceDao
) {

    suspend fun getSavedPlaces() = withContext(IO) {
        placeDao.getAll()
    }

    suspend fun savePlace(place: LocalPlace) = withContext(IO) {
        placeDao.addPlace(place)
    }

    suspend fun getForecast(location: Location) = withContext(IO) {
        apiService.getForecast("${location.latitude},${location.longitude}").await()
    }
}
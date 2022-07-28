package com.amartya.weather.view_models

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amartya.weather.models.local.LocalPlace
import com.amartya.weather.models.remote.Weather
import com.amartya.weather.repositories.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Amartya Ganguly on 27/06/22.
 */

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _savedPlaces = MutableStateFlow<List<LocalPlace>?>(null)
    val savedPlaces: StateFlow<List<LocalPlace>?> = _savedPlaces

    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    init {
        getSavedPlaces()
    }

    private fun getSavedPlaces() {
        viewModelScope.launch {
            // call repo for saved places
            _savedPlaces.value = weatherRepository.getSavedPlaces()
        }
    }

    fun getForecast(location: Location, isCurrentLocation: Boolean = false) {
        // call repo for forecast info for a location
        viewModelScope.launch {
            _weather.value = weatherRepository.getForecast(location).also {
                weatherRepository.savePlace(
                    LocalPlace(
                        name = it.location.name,
                        region = it.location.region,
                        country = it.location.country,
                        tempC = it.current.temp_c,
                        tempF = it.current.temp_f,
                        isDay = it.current.is_day == 1,
                        condition = it.current.condition.text,
                        icon = it.current.condition.icon,
                        lat = it.location.lat,
                        lon = it.location.lon
                    )
                )
            }
        }
    }
}
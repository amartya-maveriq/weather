package com.amartya.weather.models.remote

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)
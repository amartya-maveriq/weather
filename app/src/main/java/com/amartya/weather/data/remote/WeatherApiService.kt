package com.amartya.weather.data.remote

import com.amartya.weather.models.remote.PlaceItem
import com.amartya.weather.models.remote.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Amartya Ganguly on 27/06/22.
 */
interface WeatherApiService {

    @GET(RemoteApi.FORECAST)
    fun getForecast(
        @Query("q") query: String,
        @Query("days") days: Int = 3
    ): Call<Weather>

    @GET(RemoteApi.SEARCH)
    fun searchPlace(
        @Query("q") query: String
    ): Call<List<PlaceItem>>
}
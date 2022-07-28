package com.amartya.weather.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Amartya Ganguly on 28/06/22.
 * This item is to be used with the local DB
 */

@Entity
data class LocalPlace(
    @PrimaryKey val name: String = "",
    val region: String = "",
    val country: String = "",
    val tempC: Double = 0.0,
    val tempF: Double = 0.0,
    val isDay: Boolean = false,
    val condition: String = "",
    val icon: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0
)
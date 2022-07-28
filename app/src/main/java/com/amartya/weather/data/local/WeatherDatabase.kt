package com.amartya.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amartya.weather.models.local.LocalPlace


/**
 * Created by Amartya Ganguly on 28/06/22.
 */
@Database(entities = [LocalPlace::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}
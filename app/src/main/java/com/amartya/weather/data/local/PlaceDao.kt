package com.amartya.weather.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amartya.weather.models.local.LocalPlace


/**
 * Created by Amartya Ganguly on 28/06/22.
 */

@Dao
interface PlaceDao {

    @Query("SELECT * FROM localplace")
    fun getAll(): List<LocalPlace>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlace(place: LocalPlace)

    @Delete
    fun deletePlace(place: LocalPlace)
}
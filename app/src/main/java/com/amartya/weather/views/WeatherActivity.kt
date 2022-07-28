package com.amartya.weather.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amartya.weather.databinding.ActivityWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Amartya Ganguly on 27/06/22.
 */

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
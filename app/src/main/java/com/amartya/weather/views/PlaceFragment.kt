package com.amartya.weather.views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.amartya.weather.R
import com.amartya.weather.databinding.FragmentPlaceBinding
import com.amartya.weather.models.local.LocalPlace
import com.amartya.weather.view_models.WeatherViewModel
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


/**
 * Created by Amartya Ganguly on 27/06/22.
 */
@AndroidEntryPoint
class PlaceFragment(
    private val index: Int,
    private val place: LocalPlace
) : Fragment(R.layout.fragment_place) {

    private lateinit var binding: FragmentPlaceBinding
    private val locationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val viewModel by activityViewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPlaceBinding.bind(view)

        if (index == 0) {
            // this must be the first page which shows weather for current location
            // so let's fetch the current location
            if (hasPermissions()) {
                fetchLocation()
            } else {
                locationPermissionRequest.launch(PERMISSIONS)
            }
        } else {
            // this can be any of the saved places
            viewModel.getForecast(Location("").also {
                it.latitude = place.lat
                it.longitude = place.lon
            })
        }

        observeViewModel()
    }

    private fun observeViewModel() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.weather.collectLatest { weather ->
                weather?.let {
                    // todo update UI with it
                    Log.i("remote", "$it")
                }
            }
        }
    }

    private fun fetchLocation() {
        lifecycleScope.launch {
            val location = locationProviderClient.getCurrentLocation(
                LocationRequest.QUALITY_HIGH_ACCURACY,
                null
            )
            viewModel.getForecast(location.await(), isCurrentLocation = index == 0)
        }
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionsMap ->
            when {
                PERMISSIONS.all {
                    permissionsMap.getOrDefault(it, false)
                } -> {
                    // permission granted
                    fetchLocation()
                }
                else -> {
                    // permission not granted
                    // TODO access nw location which is not accurate
                }
            }
        }

    private fun hasPermissions(): Boolean {
        return PERMISSIONS.all {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}
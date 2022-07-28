package com.amartya.weather.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.amartya.weather.R
import com.amartya.weather.adapters.PlacesPagerAdapter
import com.amartya.weather.databinding.FragmentHomeBinding
import com.amartya.weather.models.local.LocalPlace
import com.amartya.weather.utils.ZoomOutPageTransformer
import com.amartya.weather.view_models.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Amartya Ganguly on 28/06/22.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)

        observe()
    }

    private fun observe() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.savedPlaces.collectLatest { localPlaces ->
                localPlaces?.let {
                    localPlaces.forEach { place -> Log.i("local", "$place") }
                    setupUi(it)
                }
            }
        }
    }

    private fun setupUi(localPlaces: List<LocalPlace>) {
        if (localPlaces.isEmpty()) {
            // this is the first time app is running
            val place = LocalPlace()
            setupPager(listOf(place))
        } else {
            setupPager(localPlaces)
        }
    }

    private fun setupPager(localPlaces: List<LocalPlace>) {
        binding.placesPager.apply {
            adapter = PlacesPagerAdapter(
                places = localPlaces,
                fragmentActivity = requireActivity()
            )
            setPageTransformer(ZoomOutPageTransformer())
        }
    }
}
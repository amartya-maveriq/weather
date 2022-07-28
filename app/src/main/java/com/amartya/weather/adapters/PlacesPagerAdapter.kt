package com.amartya.weather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amartya.weather.models.local.LocalPlace
import com.amartya.weather.views.PlaceFragment


/**
 * Created by Amartya Ganguly on 27/06/22.
 */
class PlacesPagerAdapter(
    private val places: List<LocalPlace>,
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return places.size
    }

    override fun createFragment(position: Int): Fragment {
        return PlaceFragment(index = position, place = places[position])
    }
}
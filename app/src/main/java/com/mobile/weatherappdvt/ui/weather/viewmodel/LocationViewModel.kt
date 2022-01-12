package com.mobile.weatherappdvt.ui.weather.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.util.Event
import com.mobile.weatherappdvt.util.KEY_CITY_NAME
import com.mobile.weatherappdvt.util.SharedPrefsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val sharedPrefsUtils: SharedPrefsUtils) : ViewModel() {

    val cityName = MutableLiveData<String?>()

    val location = MutableLiveData<Event<Location>>()

    fun setCityName(city: String?) {
        if (city != null) {
            sharedPrefsUtils.savePrefs(KEY_CITY_NAME, city)
            cityName.value = city
        }
    }

    init {
        val city = sharedPrefsUtils.getPrefs(KEY_CITY_NAME)
        cityName.value = city
    }
}
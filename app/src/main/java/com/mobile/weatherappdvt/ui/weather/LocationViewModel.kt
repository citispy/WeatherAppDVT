package com.mobile.weatherappdvt.ui.weather

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.util.KEY_CITY_NAME
import com.mobile.weatherappdvt.util.SharedPrefsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val sharedPrefsUtils: SharedPrefsUtils) : ViewModel() {

    val cityName = MutableLiveData<String?>()

    val location = MutableLiveData<Location>()

    fun setCityName(city: String?) {
        if (city != null) {
            cityName.value = city
            sharedPrefsUtils.savePrefs(KEY_CITY_NAME, city)
        }
    }

    init {
        val city = sharedPrefsUtils.getPrefs(KEY_CITY_NAME)
        if (city != null) {
            cityName.value = city
        }
    }
}
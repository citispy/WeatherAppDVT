package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.ui.weather.api.WeatherRequestManager
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(private val webRequestRequestManager: WeatherRequestManager): WeatherRepository {

    override val currentWeather: MutableLiveData<CurrentWeatherInfo> = webRequestRequestManager.currentWeatherInfo
    override val isLoading: LiveData<Boolean> = webRequestRequestManager.isLoading

    override fun getCurrentWeather(lat: Double, lon: Double) {
        webRequestRequestManager.getCurrentWeatherInfo(lat, lon)
    }
}
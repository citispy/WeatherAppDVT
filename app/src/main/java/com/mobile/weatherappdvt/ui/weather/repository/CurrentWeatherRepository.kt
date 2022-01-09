package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.ui.weather.api.WeatherRequestManager
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(private val webRequestRequestManager: WeatherRequestManager): WeatherRepository {

    override val currentWeather: MutableLiveData<CurrentWeatherInfo> = webRequestRequestManager.currentWeatherInfo

    override fun getCurrentWeather(cityName: String) {
        webRequestRequestManager.getCurrentWeatherInfo(cityName)
    }
}
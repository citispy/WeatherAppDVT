package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.api.WebRequestManager
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(private val webRequestManager: WebRequestManager): WeatherRepository {

    override val currentWeather: MutableLiveData<CurrentWeatherInfo> = webRequestManager.currentWeatherInfo

    override fun getCurrentWeather(cityName: String) {
        webRequestManager.getCurrentWeatherInfo(cityName)
    }
}
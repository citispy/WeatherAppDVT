package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.CurrentWeatherInfo

interface WeatherRepository {
    val currentWeather: MutableLiveData<CurrentWeatherInfo>
    fun getCurrentWeather(cityName: String)
}
package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.CurrentWeatherInfo

interface WeatherRepository {
    val currentWeather: LiveData<CurrentWeatherInfo>
    val isLoading: LiveData<Boolean>
    fun getCurrentWeather(cityName: String?)
}
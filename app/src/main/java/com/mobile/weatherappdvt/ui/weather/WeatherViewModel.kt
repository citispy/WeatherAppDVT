package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.model.CurrentWeatherInfo

class WeatherViewModel constructor(repository: WeatherRepository): ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather
    val currentTemp = Transformations.map(currentWeatherInfo) {
        it.main.feelsLike
    }
    val minTemp = Transformations.map(currentWeatherInfo) {
        it.main.feelsLike
    }
    val maxTemp = Transformations.map(currentWeatherInfo) {
        it.main.feelsLike
    }
    val weatherDescription = Transformations.map(currentWeatherInfo) {
        it.weather[0].main
    }

    init {
        repository.getCurrentWeather()
    }
}
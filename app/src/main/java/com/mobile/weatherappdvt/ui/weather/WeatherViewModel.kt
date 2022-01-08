package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.model.CurrentWeatherInfo

class WeatherViewModel constructor(repository: WeatherRepository): ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather
    val currentTemp: LiveData<Double?> = Transformations.map(currentWeatherInfo) {
        it.main?.temp
    }
    val minTemp: LiveData<Double?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMin
    }
    val maxTemp: LiveData<Double?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMax
    }
    val weatherDescription: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.weather?.get(0)?.main
    }

    val errorMessage: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.errorMessage
    }

    init {
        repository.getCurrentWeather()
    }
}
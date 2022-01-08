package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(repository: WeatherRepository): ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather
    val currentTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.temp?.toInt().toString()
    }
    val minTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMin?.toInt().toString()
    }
    val maxTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMax?.toInt().toString()
    }
    val weatherDescription: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.weather?.get(0)?.main
    }

    val errorMessage: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.errorMessage
    }

    init {
        repository.getCurrentWeather("Cape Town")
    }
}
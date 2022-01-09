package com.mobile.weatherappdvt.ui.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import com.mobile.weatherappdvt.ui.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val RAIN = "Rain"
private const val CLEAR = "Clear"
private const val CLOUDS = "Clouds"

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather

    val currentTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.temp?.toInt()?.toString()
    }

    val minTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMin?.toInt()?.toString()
    }

    val maxTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.main?.tempMax?.toInt()?.toString()
    }

    val weatherDescription: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.weather?.get(0)?.main
    }

    val errorMessage: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.errorMessage
    }

    val imageDrawable: LiveData<Int?> = Transformations.map(currentWeatherInfo) {
            when(it.weather?.get(0)?.main) {
                RAIN -> R.drawable.forest_rainy
                CLEAR -> R.drawable.forest_sunny
                CLOUDS -> R.drawable.forest_cloudy
                else -> null
            }
    }

    val backgroundColor: LiveData<Int?> = Transformations.map(currentWeatherInfo) {
        when(it.weather?.get(0)?.main) {
            RAIN -> R.color.rain_dark_grey
            CLEAR -> R.color.clear_green
            CLOUDS -> R.color.clouds_grey
            else -> null
        }
    }

    fun getCurrentWeather(lat: Double, lon: Double) {
        repository.getCurrentWeather(lat, lon)
    }
}
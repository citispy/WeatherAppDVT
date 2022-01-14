package com.mobile.weatherappdvt.ui.weather.viewmodel

import android.location.Location
import androidx.lifecycle.*
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import com.mobile.weatherappdvt.ui.weather.repository.WeatherRepository
import com.mobile.weatherappdvt.util.Constants.CLEAR
import com.mobile.weatherappdvt.util.Constants.CLOUDS
import com.mobile.weatherappdvt.util.Constants.RAIN
import com.mobile.weatherappdvt.util.FormatUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather

    val isLoading: LiveData<Boolean> = repository.isLoading

    val currentTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val minTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val maxTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val weatherDescription: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.weather?.get(0)?.main
    }

    val errorMessage: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.errorMessage
    }

    val imageDrawable: LiveData<Int?> = Transformations.map(currentWeatherInfo) {
        when (it.weather?.get(0)?.main) {
            RAIN -> R.drawable.forest_rainy
            CLEAR -> R.drawable.forest_sunny
            CLOUDS -> R.drawable.forest_cloudy
            else -> null
        }
    }

    val backgroundColor: LiveData<Int?> = Transformations.map(currentWeatherInfo) {
        when (it.weather?.get(0)?.main) {
            RAIN -> R.color.rain_dark_grey
            CLEAR -> R.color.clear_green
            CLOUDS -> R.color.clouds_grey
            else -> null
        }
    }

    fun getCurrentWeather(cityName: String?) {
        repository.getCurrentWeather(cityName)
    }
}
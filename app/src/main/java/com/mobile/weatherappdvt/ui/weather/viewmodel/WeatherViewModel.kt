package com.mobile.weatherappdvt.ui.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import com.mobile.weatherappdvt.ui.weather.repository.WeatherRepository
import com.mobile.weatherappdvt.util.Constants.CLEAR
import com.mobile.weatherappdvt.util.Constants.CLOUDS
import com.mobile.weatherappdvt.util.Constants.RAIN
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather

    private val isLoading: LiveData<Boolean> = repository.isLoading

    val uiState = MediatorLiveData<UiState>()

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

    fun haveAllWeatherInfo() : Boolean {
        return currentTemp.value != null &&
                minTemp.value !== null &&
                maxTemp.value != null &&
                weatherDescription.value != null
    }

    init {
        uiState.addSource(isLoading) {
            if (it) {
                uiState.value = UiState.LOADING
            }
        }

        uiState.addSource(errorMessage) {
            if (it == null) {
                uiState.value = UiState.SUCCESSFULLY_RETRIEVED_DATA
            } else {
                uiState.value = UiState.ERROR_MESSAGE_RECEIVED
            }
        }
    }

    enum class UiState() {
        LOADING, ERROR_MESSAGE_RECEIVED, SUCCESSFULLY_RETRIEVED_DATA
    }
}
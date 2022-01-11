package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.LiveData
import com.mobile.weatherappdvt.model.FiveDayForecast

interface ForecastRepository {
    val fiveDayForecast: LiveData<FiveDayForecast>
    val isLoading: LiveData<Boolean>
    fun getForecast(cityName: String?)
}
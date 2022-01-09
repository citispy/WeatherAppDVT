package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.FiveDayForecast
import com.mobile.weatherappdvt.ui.weather.api.ForecastRequestManager
import javax.inject.Inject

class ForecastWeatherRepository @Inject constructor(private val forecastRequestManager: ForecastRequestManager): ForecastRepository {
    override val fiveDayForecast: LiveData<FiveDayForecast> =  forecastRequestManager.fiveDayForecast

    override fun getForecast(lat: Double, lon: Double) {
        forecastRequestManager.getForecast(lat, lon)
    }
}
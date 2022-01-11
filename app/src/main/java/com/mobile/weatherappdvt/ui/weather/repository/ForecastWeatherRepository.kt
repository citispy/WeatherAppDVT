package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.LiveData
import com.mobile.weatherappdvt.model.FiveDayForecast
import com.mobile.weatherappdvt.ui.weather.api.ForecastRequestManager
import javax.inject.Inject

class ForecastWeatherRepository @Inject constructor(private val forecastRequestManager: ForecastRequestManager): ForecastRepository {
    override val fiveDayForecast: LiveData<FiveDayForecast> =  forecastRequestManager.fiveDayForecast
    override val isLoading = forecastRequestManager.isLoading

    override fun getForecast(cityName: String?) {
        forecastRequestManager.getForecast(cityName)
    }
}
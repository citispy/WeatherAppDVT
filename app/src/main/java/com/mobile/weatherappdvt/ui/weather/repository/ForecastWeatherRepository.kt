package com.mobile.weatherappdvt.ui.weather.repository

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.FiveDayForecast

class ForecastWeatherRepository: ForecastRepository {
    override val fiveDayForecast =  MutableLiveData<FiveDayForecast>()

    override fun getForecast() {

    }
}
package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.CurrentWeatherInfo

class CurrentWeatherRepository: WeatherRepository {
    override val currentWeather: MutableLiveData<CurrentWeatherInfo> = MutableLiveData()

    override fun getCurrentWeather() {

    }

}
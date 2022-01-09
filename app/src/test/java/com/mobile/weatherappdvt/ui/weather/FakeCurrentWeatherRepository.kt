package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import com.mobile.weatherappdvt.ui.weather.repository.WeatherRepository
import com.mobile.weatherappdvt.util.MockResponseFileReader

class FakeCurrentWeatherRepository(private val path: String): WeatherRepository {
    override val currentWeather =  MutableLiveData<CurrentWeatherInfo>()

    override fun getCurrentWeather(lat: Double, lon: Double) {
        val reader = MockResponseFileReader(path)
        val gson = Gson()
        val currentWeatherInfo = gson.fromJson(reader.content, CurrentWeatherInfo::class.java)
        currentWeather.value = currentWeatherInfo
    }

    companion object {
        fun forSuccessfulResponse(): FakeCurrentWeatherRepository = FakeCurrentWeatherRepository("successful_current_weather_response.json")
        fun forFailedResponse(): FakeCurrentWeatherRepository = FakeCurrentWeatherRepository("failed_response.json")
    }
}
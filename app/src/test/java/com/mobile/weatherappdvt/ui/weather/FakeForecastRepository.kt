package com.mobile.weatherappdvt.ui.weather

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mobile.weatherappdvt.model.FiveDayForecast
import com.mobile.weatherappdvt.ui.weather.repository.ForecastRepository
import com.mobile.weatherappdvt.utils.MockResponseFileReader

class FakeForecastRepository(private val path: String): ForecastRepository {
    override val fiveDayForecast = MutableLiveData<FiveDayForecast>()

    override fun getForecast() {
        val reader = MockResponseFileReader(path)
        val gson = Gson()
        val forecast = gson.fromJson(reader.content, FiveDayForecast::class.java)
        fiveDayForecast.value = forecast
    }

    companion object {
        fun forSuccessfulResponse(): FakeForecastRepository = FakeForecastRepository("successful_forecast_response.json")
        fun forFailedResponse(): FakeForecastRepository = FakeForecastRepository("failed_response.json")
    }
}
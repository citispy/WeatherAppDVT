package com.mobile.weatherappdvt.api

import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getCurrentWeatherInfo(@Query("q") cityName: String, @Query("units") units: String = "metric"): Call<CurrentWeatherInfo>

}
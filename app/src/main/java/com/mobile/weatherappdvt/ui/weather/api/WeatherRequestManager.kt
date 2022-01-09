package com.mobile.weatherappdvt.ui.weather.api

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.api.ApiInterface
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRequestManager @Inject constructor(private val apiInterface: ApiInterface) {

    val currentWeatherInfo = MutableLiveData<CurrentWeatherInfo>()
    val isLoading = MutableLiveData<Boolean>()

    fun getCurrentWeatherInfo(lat: Double, lon: Double) {
        setIsLoading(true)

        apiInterface.getCurrentWeatherInfo(lat, lon).enqueue(object : Callback<CurrentWeatherInfo> {
            override fun onResponse(call: Call<CurrentWeatherInfo>, response: Response<CurrentWeatherInfo>) {
                currentWeatherInfo.value = response.body()
                setIsLoading(false)
            }

            override fun onFailure(call: Call<CurrentWeatherInfo>, t: Throwable) {
                currentWeatherInfo.value = CurrentWeatherInfo(errorMessage = "There was an error")
                setIsLoading(false)
            }
        })
    }

    private fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }
}
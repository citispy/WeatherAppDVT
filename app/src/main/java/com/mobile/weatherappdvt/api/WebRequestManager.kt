package com.mobile.weatherappdvt.api

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.model.CurrentWeatherInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WebRequestManager @Inject constructor(private val apiInterface: ApiInterface) {

    val currentWeatherInfo = MutableLiveData<CurrentWeatherInfo>()

    fun getCurrentWeatherInfo(cityName: String) {
        apiInterface.getCurrentWeatherInfo(cityName).enqueue(object : Callback<CurrentWeatherInfo> {
            override fun onResponse(call: Call<CurrentWeatherInfo>, response: Response<CurrentWeatherInfo>) {
                currentWeatherInfo.value = response.body()
            }

            override fun onFailure(call: Call<CurrentWeatherInfo>, t: Throwable) {
                currentWeatherInfo.value = CurrentWeatherInfo(errorMessage = "There was an error")
            }
        })
    }
}
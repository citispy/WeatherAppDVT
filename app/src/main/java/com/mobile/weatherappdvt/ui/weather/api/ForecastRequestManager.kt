package com.mobile.weatherappdvt.ui.weather.api

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappdvt.api.ABaseRequestManager
import com.mobile.weatherappdvt.api.ApiInterface
import com.mobile.weatherappdvt.model.FiveDayForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ForecastRequestManager @Inject constructor(private val apiInterface: ApiInterface) : ABaseRequestManager() {

    val fiveDayForecast = MutableLiveData<FiveDayForecast>()

    fun getForecast(cityName: String?) {
        setIsLoading(true)

        apiInterface.getForecast(cityName).enqueue(object : Callback<FiveDayForecast> {
            override fun onResponse(call: Call<FiveDayForecast>, response: Response<FiveDayForecast>) {
                fiveDayForecast.value = response.body()
                setIsLoading(false)
            }

            override fun onFailure(call: Call<FiveDayForecast>, t: Throwable) {
                fiveDayForecast.value = FiveDayForecast(message = "There was an error")
                setIsLoading(false)
            }
        })
    }
}
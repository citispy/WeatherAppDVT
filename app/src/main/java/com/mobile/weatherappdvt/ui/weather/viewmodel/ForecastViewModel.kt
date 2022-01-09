package com.mobile.weatherappdvt.ui.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.model.FiveDayForecast
import com.mobile.weatherappdvt.model.ForecastListItem
import com.mobile.weatherappdvt.ui.weather.repository.ForecastRepository
import com.mobile.weatherappdvt.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val RAIN = "Rain"
private const val CLEAR = "Clear"
private const val CLOUDS = "Clouds"

@HiltViewModel
class ForecastViewModel @Inject constructor(repository: ForecastRepository): ViewModel() {

    private val fiveDayForecast: LiveData<FiveDayForecast> = repository.fiveDayForecast

    val forecast: LiveData<ArrayList<ForecastListItem>> = Transformations.map(fiveDayForecast) {
        val list: ArrayList<ForecastListItem> = ArrayList()
        populateList(it, list)
        list
    }

    val message = Transformations.map(fiveDayForecast) {
        it.message
    }

    private fun populateList(it: FiveDayForecast, list: ArrayList<ForecastListItem>) {
        val forecasts = it.list
        if (forecasts != null) {
            for (i in forecasts.indices) {
                val forecast = forecasts[i]

                //Don't add forecast for today
                if (!DateUtils.isNotToday(forecast.dtTxt)) {
                    continue
                }

                val main = forecast.main

                val temp = main?.temp?.toInt()?.toString()
                val day = DateUtils.getDayForDate(forecast.dtTxt)
                val weatherDescription = forecast.weather?.get(0)?.main
                val imageDrawable = getImageDrawable(weatherDescription)

                if (temp != null && day != null && imageDrawable != null) {
                    val item = ForecastListItem(temp, day, imageDrawable)
                    addHighestTempPerDay(list, item)
                }
            }
        }
    }

    private fun addHighestTempPerDay(list: ArrayList<ForecastListItem>, newItem: ForecastListItem) {
        if (list.isEmpty()) {
            list.add(newItem)
            return
        }

        val lastListItem: ForecastListItem = list.last()
        if (lastListItem.day == newItem.day) {
            if (lastListItem.temp <= newItem.temp) {
                list[list.lastIndex] = newItem
            }
        } else {
            list.add(newItem)
        }
    }

    private fun getImageDrawable(weatherDescription: String?): Int? {
        return when(weatherDescription) {
            CLOUDS -> R.drawable.partlysunny
            RAIN -> R.drawable.rain
            CLEAR -> R.drawable.clear
            else -> null
        }
    }

    init {
        repository.getForecast()
    }
}
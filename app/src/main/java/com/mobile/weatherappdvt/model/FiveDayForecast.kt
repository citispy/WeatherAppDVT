package com.mobile.weatherappdvt.model


import com.google.gson.annotations.SerializedName

data class FiveDayForecast(
    @SerializedName("cod")
    val cod: String? = null,
    @SerializedName("list")
    val list: List<Forecast>? = null,
    @SerializedName("message")
    val message: Int? = null,
    @SerializedName("message")
    val errorMessage: String? = null
)
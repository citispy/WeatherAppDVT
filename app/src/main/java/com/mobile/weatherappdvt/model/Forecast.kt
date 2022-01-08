package com.mobile.weatherappdvt.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("pop")
    val pop: Int?,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("weather")
    val weather: List<Weather>?,
)

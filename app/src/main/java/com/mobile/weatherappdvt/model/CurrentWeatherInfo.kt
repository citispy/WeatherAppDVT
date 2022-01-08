package com.mobile.weatherappdvt.model


import com.google.gson.annotations.SerializedName

data class CurrentWeatherInfo(
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("cod")
    val cod: Int? = null,
    @SerializedName("dt")
    val dt: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("timezone")
    val timezone: Int? = null,
    @SerializedName("visibility")
    val visibility: Int? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("message")
    val errorMessage: String? = null
)
package com.example.todayweather.home.model

data class WeatherGetApi(
    val current: Current,
    val daily: MutableList<Daily>,
    val hourly: MutableList<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
)
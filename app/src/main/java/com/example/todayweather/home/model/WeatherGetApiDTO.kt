package com.example.todayweather.home.model

data class WeatherGetApiDTO(
    val current: String,
    val daily: String,
    val hourly: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)
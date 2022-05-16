package com.example.todayweather.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherGetApi(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
)
package com.example.todayweather.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "get_api_table")
data class WeatherGetApiDTO(
    @PrimaryKey val id: Int=1,
    val current: String,
    val daily: String,
    val hourly: String,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)
package com.example.todayweather.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "get_api_table")
data class WeatherGetApi(
    @PrimaryKey val id: Int=1,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
)
package com.example.todayweather.home.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "get_api_table")
data class WeatherGetApi(
    val current: Current,
    val daily: MutableList<Daily>,
    val hourly: MutableList<Hourly>,
    var lat: Double,
    var lon: Double,
    val timezone: String,
    val timezone_offset: Int
) {
    @PrimaryKey
    var id = 1
}
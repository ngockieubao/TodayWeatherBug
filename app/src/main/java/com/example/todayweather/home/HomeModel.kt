package com.example.todayweather.home

data class HomeModel(
//    val base: String,
//    val clouds: Clouds,
//    val cod: Int,
//    val coord: Coord,
//    val dt: Int,
//    val id: Int,
//    val main: Main,
//    val name: String,
//    val sys: Sys,
//    val timezone: Int,
//    val visibility: Int,
//    val weather: List<Weather>,
//    val wind: Wind,
    // Detail
    val icon: Int? = null,
    val description: String? = null,
    val result: String? = null,
    // Everyday
    val temp: String? = null,
    val humidity: String? = null,
    val iconStatus: Int? = null,
    val wind: String? = null,
    val iconWindSpeed: Int? = null,
    val time: String? = null
)
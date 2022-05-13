package com.example.todayweather.home

data class HomeModel(
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
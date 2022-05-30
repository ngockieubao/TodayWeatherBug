package com.example.todayweather.home.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("city")
    val city: String
)
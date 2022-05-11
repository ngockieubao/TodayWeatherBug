package com.example.todayweather.everyday

import android.widget.ImageView

data class EverydayModel(
    val temp: String,
    val humidity: String,
    val iconStatus: Int,
    val wind: String,
    val iconWindSpeed: Int,
    val time: String
)
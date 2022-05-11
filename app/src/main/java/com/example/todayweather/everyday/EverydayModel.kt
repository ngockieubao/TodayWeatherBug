package com.example.todayweather.everyday

import android.widget.ImageView

data class EverydayModel(
    val temp: String,
    val humidity: String,
    val iconStatus: ImageView,
    val wind: String,
    val iconPosition: ImageView,
    val time: Int
)
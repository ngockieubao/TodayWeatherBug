package com.example.todayweather

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setTemp")
fun TextView.setTemp(temp: Double) {
    this.text = context.getString(R.string.temp_C, temp)
}

@BindingAdapter("setPop")
fun TextView.setPop(pop: Double) {
    if (pop != 0.0)
        this.text = context.getString(R.string.pop, pop * 100)
    else
        this.text = " "
}

@BindingAdapter("setWindSpeed")
fun TextView.setWindSpeed(windSpeed: Double) {
    this.text = context.getString(R.string.wind_speed, windSpeed * 3600 / 1000)
}

@BindingAdapter("setIconCloud")
fun ImageView.setIconCloud(url: Int) {
    this.setImageResource(url)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setTime")
fun TextView.setTime(dt: Long) {
    val date = Date(dt * 1000)
    val formatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    this.text = formatTime.format(date)
}


package com.example.todayweather.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setIconCloud")
fun ImageView.setIconCloud(url: Int) {
    this.setImageResource(url)
}

@BindingAdapter("setTemp")
fun TextView.setTemp(temp: Double) {
    this.text = Utils.formatTemp(temp)
}

@BindingAdapter("setPop")
fun TextView.setPop(pop: Double) {
    this.text = Utils.formatPop(pop)
}

@BindingAdapter("setWindSpeed")
fun TextView.setWindSpeed(windSpeed: Double) {
    this.text = Utils.formatWindSpeed(windSpeed)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setTime")
fun TextView.setTime(dt: Long) {
    this.text = Utils.formatTime(dt)
}

@BindingAdapter("setDate")
fun TextView.setDate(dt: Long) {
    this.text = Utils.formatDate(dt)
}


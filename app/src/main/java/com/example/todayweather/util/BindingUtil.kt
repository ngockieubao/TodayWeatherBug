package com.example.todayweather.util

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setIcon")
fun ImageView.setIcon(url: String?) {
    val urlPrefix = "http://openweathermap.org/img/wn/"
    val urlSuffix = "@2x.png"
    Glide.with(this.context)
        .load("$urlPrefix$url$urlSuffix")
        .into(this)
}

@BindingAdapter("setTemp")
fun TextView.setTemp(temp: Double) {
    this.text = Utils.formatTemp(context, temp)
}

@BindingAdapter("setPop")
fun TextView.setPop(pop: Double) {
    this.text = Utils.formatPop(context, pop)
}

@BindingAdapter("setWindSpeed")
fun TextView.setWindSpeed(windSpeed: Double) {
    this.text = Utils.formatWindSpeed(context, windSpeed)
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("setTime")
fun TextView.setTime(dt: Long) {
    this.text = Utils.formatTime(context, dt)
}

@BindingAdapter("setDate")
fun TextView.setDate(dt: Long) {
    this.text = Utils.formatDate(context, dt)
}

@BindingAdapter(value = ["setTempMax", "setTempMin"])
fun TextView.setTempMaxMin(tempMax: Double, tempMin: Double) {
    this.text = Utils.formatTempMaxMin(context, tempMax, tempMin)
}

@BindingAdapter(value = ["setTempCurrent", "setFeelsLike"])
fun TextView.setTempFeelsLike(temp: Double, feelsLike: Double) {
    this.text = Utils.formatTempFeelsLike(context, temp, feelsLike)
}

@BindingAdapter("setStatus")
fun TextView.setStatus(description: String) {
    this.text = description
}

@BindingAdapter("setWindStatus")
fun TextView.setWindStatus(description: String){
    this.text = description

}

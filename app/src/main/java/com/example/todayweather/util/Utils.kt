package com.example.todayweather.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatTime(time: Long): String {
        val getTime = Date(time.times(1000))
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(getTime)
    }

    fun formatDate(date: Long): String {
        val dateFormat = Date(date.times(1000))
        val dayOfWeek = SimpleDateFormat("EEE", Locale.getDefault())
        var dayOfWeekFormat = dayOfWeek.format(dateFormat)
        dayOfWeekFormat = when (dayOfWeekFormat) {
            "Mon" -> "THỨ HAI"
            "Tue" -> "THỨ BA"
            "Wed" -> "THỨ TƯ"
            "Thu" -> "THỨ NĂM"
            "Fri" -> "THỨ SÁU"
            "Sat" -> "THỨ BẢY"
            "Sun" -> "CHỦ NHẬT"
            else
            -> "BUG!!!"
        }
        val dayMonth = SimpleDateFormat("dd 'THÁNG' M", Locale.getDefault())
        val dayMonthFormat = dayMonth.format(dateFormat)
        return "$dayOfWeekFormat, $dayMonthFormat"
    }

    fun formatPop(pop: Double): String {
        val popFormat = String.format("%.0f%%", pop.times(100))
        return if (pop != 0.0)
            popFormat
        else
            ""
    }

    fun formatTempFeelsLike(temp: Double, tempFeelsLike: Double): String {
        val tempFormat = String.format("%.0f", temp)
        val tempFeelsLikeFormat = String.format("%.0f", tempFeelsLike)
        return "$tempFormat°C - Cảm Giác Như: $tempFeelsLikeFormat°C"
    }

    fun formatTempMaxMin(tempMax: Double, tempMin: Double): String {
        val tempMaxFormat = String.format("%.0f", tempMax)
        val tempMinFormat = String.format("%.0f", tempMin)
        return "$tempMaxFormat° / $tempMinFormat°"
    }

    fun formatTemp(temp: Double): String {
        return String.format("%.0f°C", temp)
    }

    fun formatWind(wind: Double): String {
        return String.format("Gió: %.0fkm/h •", wind)
    }

    fun formatWindSpeed(windSpeed: Double): String {
        return String.format("%.0f km/h", windSpeed.times(3600).div(1000))
    }
}
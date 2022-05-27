package com.example.todayweather.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.todayweather.broadcast.NotificationReceiver
import com.example.todayweather.R
import com.example.todayweather.home.model.Daily
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object Utils {
    fun formatTime(context: Context, time: Long): String {
        val getTime = Date(time.times(1000))
        val timeFormat = SimpleDateFormat(context.getString(R.string.fm_time), Locale.getDefault())
        return timeFormat.format(getTime)
    }

    fun formatDate(context: Context, date: Long): String {
        val dateFormat = Date(date.times(1000))
        val dayOfWeek = SimpleDateFormat(context.getString(R.string.fm_day_of_week), Locale("vi"))
        val dayOfWeekFormat = dayOfWeek.format(dateFormat)
        val dayMonth = SimpleDateFormat(context.getString(R.string.fm_date), Locale("vi"))
        val dayMonthFormat = dayMonth.format(dateFormat)
        return String.format(context.getString(R.string.fm_day_date), dayOfWeekFormat, dayMonthFormat)
    }

    fun formatPop(context: Context, pop: Double): String {
        val popFormat = String.format(context.getString(R.string.fm_pop), pop.times(100))
        return if (pop != 0.0)
            popFormat
        else
            String.format(context.getString(R.string.string_empty))
    }

    fun formatTempFeelsLike(context: Context, temp: Double, tempFeelsLike: Double): String {
        val tempFormat = String.format(context.getString(R.string.fm_temp), temp)
        val tempFeelsLikeFormat = String.format(context.getString(R.string.fm_temp), tempFeelsLike)
        return String.format(context.getString(R.string.fm_temp_feels_like), tempFormat, tempFeelsLikeFormat)
    }

    fun formatTempMaxMin(context: Context, tempMax: Double, tempMin: Double): String {
        val tempMaxFormat = String.format(context.getString(R.string.fm_temp), tempMax)
        val tempMinFormat = String.format(context.getString(R.string.fm_temp), tempMin)
        return String.format(context.getString(R.string.fm_temp_max_min), tempMaxFormat, tempMinFormat)
    }

    fun formatTemp(context: Context, temp: Double): String {
        return String.format(context.getString(R.string.fm_temp_celsius), temp)
    }

    fun formatWindSpeed(context: Context, windSpeed: Double): String {
        return String.format(context.getString(R.string.fm_wind_speed), windSpeed.times(3600).div(1000))
    }

    fun formatWind(context: Context, wind: Double, windDeg: Int): String {
        return String.format(context.getString(R.string.fm_wind_status), wind, formatWindDeg(context, windDeg))
    }

    fun formatWindDeg(context: Context, deg: Int): String {
        val getIndex = deg.div(22.5).plus(1).roundToInt()
        val listWindDeg = context.resources.getStringArray(R.array.wind_deg)
        return listWindDeg[getIndex.minus(1)]
    }

    fun formatHomeStatusAbove(context: Context, daily: Daily?): String {
        return String.format(context.getString(R.string.fm_string), upCaseFirstLetter(daily!!.weather[0].description))
    }

    fun formatHomeStatusBelow(context: Context, daily: Daily?): String {
        return String.format(
            context.getString(R.string.fm_status_home),
            upCaseFirstLetter(daily!!.weather[0].description),
            daily.temp.max,
            daily.temp.min,
            formatWindDeg(context, daily.wind_deg),
            daily.wind_speed,
            formatPop(context, daily.pop)
        )
    }

    fun formatDailyNavStatus(context: Context, daily: Daily?): String {
        return String.format(
            context.getString(R.string.fm_status_daily_nav),
            upCaseFirstLetter(daily!!.weather[0].description),
            daily.temp.max,
            daily.temp.min,
            formatWindDeg(context, daily.wind_deg),
            daily.wind_speed,
            formatPop(context, daily.pop)
        )
    }

    fun formatLocation(location: String): String {
        val listLocation: List<String> = location.split(",").map { it -> it.trim() }
        return "${listLocation[1]}, ${listLocation[2]}"
    }

    fun upCaseFirstLetter(letter: String): String {
        return letter.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    fun convertToBitMap(context: Context, id: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, id)
    }

    fun setAlarm(context: Context, timeOfAlarm: Long) {

        // Intent to start the Broadcast Receiver
        val broadcastIntent = Intent(
            context, NotificationReceiver::class.java
        )

        val pIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            0
        )

        // Setting up AlarmManager
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < timeOfAlarm) {
            alarmMgr.set(
                AlarmManager.RTC_WAKEUP,
                timeOfAlarm,
                pIntent
            )
        }
    }
}
package com.example.todayweather.everyday

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.todayweather.R

class EverydayViewModel(application: Application) : ViewModel() {
    private val res = application.resources
    var listData = mutableListOf<EverydayModel>()

    init {
        addData()
    }

    private fun addData() {
        val index1 = EverydayModel(
            res.getString(R.string.temperature_C, 28), res.getString(R.string.humidity, 15), 1,
            res.getString(R.string.wind_speed, 16), 1, res.getString(R.string.time, "10:00")
        )
        listData.add(index1)
        val index2 = EverydayModel(
            res.getString(R.string.temperature_C, 24), res.getString(R.string.humidity, 25), 1,
            res.getString(R.string.wind_speed, 15), 1, res.getString(R.string.time, "11:00")
        )
        listData.add(index2)
        val index3 = EverydayModel(
            res.getString(R.string.temperature_C, 27), res.getString(R.string.humidity, 37), 1,
            res.getString(R.string.wind_speed, 19), 1, res.getString(R.string.time, "12:00")
        )
        listData.add(index3)
        val index4 = EverydayModel(
            res.getString(R.string.temperature_C, 19), res.getString(R.string.humidity, 40), 1,
            res.getString(R.string.wind_speed, 10), 1, res.getString(R.string.time, "13:00")
        )
        listData.add(index4)
        val index5 = EverydayModel(
            res.getString(R.string.temperature_C, 26), res.getString(R.string.humidity, 29), 1,
            res.getString(R.string.wind_speed, 11), 1, res.getString(R.string.time, "14:00")
        )
        listData.add(index5)
        val index6 = EverydayModel(
            res.getString(R.string.temperature_C, 25), res.getString(R.string.humidity, 67), 1,
            res.getString(R.string.wind_speed, 26), 1, res.getString(R.string.time, "15:00")
        )
        listData.add(index6)
        val index7 = EverydayModel(
            res.getString(R.string.temperature_C, 30), res.getString(R.string.humidity, 19), 1,
            res.getString(R.string.wind_speed, 20), 1, res.getString(R.string.time, "16:00")
        )
        listData.add(index7)
        val index8 = EverydayModel(
            res.getString(R.string.temperature_C, 17), res.getString(R.string.humidity, 52), 1,
            res.getString(R.string.wind_speed, 22), 1, res.getString(R.string.time, "17:00")
        )
        listData.add(index8)
        val index9 = EverydayModel(
            res.getString(R.string.temperature_C, 20), res.getString(R.string.humidity, 48), 1,
            res.getString(R.string.wind_speed, 24), 1, res.getString(R.string.time, "18:00")
        )
        listData.add(index9)
        val index10 = EverydayModel(
            res.getString(R.string.temperature_C, 29), res.getString(R.string.humidity, 64), 1,
            res.getString(R.string.wind_speed, 27), 1, res.getString(R.string.time, "19:00")
        )
        listData.add(index10)
    }
}
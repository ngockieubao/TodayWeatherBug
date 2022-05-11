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
//        val index1 = EverydayModel(res.getString(R.string.temperature_C,28), res.getString(R.string.humidity, 15), )
    }
}
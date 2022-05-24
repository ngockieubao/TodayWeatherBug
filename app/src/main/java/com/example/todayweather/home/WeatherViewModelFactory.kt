package com.example.todayweather.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todayweather.database.WeatherDAO
import com.example.todayweather.network.WeatherApiService
import com.example.todayweather.util.WeatherApplication

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(
    private val dataSource: WeatherDAO,
    private val weatherApiService: WeatherApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(dataSource, weatherApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
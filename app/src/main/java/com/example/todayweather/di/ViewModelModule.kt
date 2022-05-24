package com.example.todayweather.di

import com.example.todayweather.home.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get(), get()) }
}
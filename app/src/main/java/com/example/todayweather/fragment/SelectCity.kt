package com.example.todayweather.fragment

import com.example.todayweather.home.model.City

interface SelectCity {
    fun selectItem(city: City?)
}
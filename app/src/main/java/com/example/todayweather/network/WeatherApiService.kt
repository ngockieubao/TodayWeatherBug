package com.example.todayweather.network

import com.example.todayweather.detailgetapi.DetailGetApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.openweathermap.org/"

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("data/2.5/weather?q=hanoi&units=metric&appid=65cfd157767c46c6f50248639d97778b")
    suspend fun getProperties(): DetailGetApi

}

object WeatherApi {
    val retrofitService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
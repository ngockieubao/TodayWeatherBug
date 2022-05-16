package com.example.todayweather.network

import com.example.todayweather.home.model.WeatherGetApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://api.openweathermap.org/"

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(logRetrofit())
    .build()

interface WeatherApiService {
    @GET("data/2.5/onecall?lat=21.5&lon=105.7&exclude=minutely,alert&appid=53fbf527d52d4d773e828243b90c1f8e&lang=vi&units=metric")
    suspend fun getProperties(): WeatherGetApi

}

private fun logRetrofit(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(logging).build()
}

object WeatherApi {
    val retrofitService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
}
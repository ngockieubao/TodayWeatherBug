package com.example.todayweather.network

import com.example.todayweather.home.model.WeatherGetApi
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"
private const val URL_PATH = "data/2.5/onecall"

// Danang_lat-lon
private const val URL_LAT = 16.047079
private const val URL_LON = 108.206230
private const val URL_EXCLUDE = "minutely,alert"
private const val URL_APPID = "53fbf527d52d4d773e828243b90c1f8e"
private const val URL_LANG = "vi"
private const val URL_UNITS = "metric"

//val retrofit: Retrofit = Retrofit.Builder()
//    .addConverterFactory(GsonConverterFactory.create())
//    .baseUrl(BASE_URL)
//    .client(logRetrofit())
//    .build()

interface WeatherApiService {
    @GET(URL_PATH)
    suspend fun getProperties(
        @Query("lat") lat: Double = URL_LAT,
        @Query("lon") lon: Double = URL_LON,
        @Query("exclude") exclude: String = URL_EXCLUDE,
        @Query("appid") appid: String = URL_APPID,
        @Query("lang") lang: String = URL_LANG,
        @Query("units") units: String = URL_UNITS
    ): WeatherGetApi
}

//private fun logRetrofit(): OkHttpClient {
//    val logging = HttpLoggingInterceptor()
//    logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
//    return OkHttpClient.Builder().addInterceptor(logging).build()
//}
//
//object WeatherApi {
//    val retrofitService: WeatherApiService = retrofit.create(WeatherApiService::class.java)
//}
package com.example.todayweather.home

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.todayweather.R

class HomeViewModel(application: Application) : ViewModel() {
    private val res = application.resources

    var listDataDetail = mutableListOf<HomeModel>()
    var listDataEveryday = mutableListOf<HomeModel>()

    init {
        addDataDetail()
        addDataEveryday()
    }

    private fun addDataDetail() {
        val detailModel = HomeModel(icon = 1, description = "Cảm Giác Như", result = res.getString(R.string.temperature_C, 38))
        listDataDetail.add(detailModel)
        val detailModel1 = HomeModel(2, "Độ Ẩm", res.getString(R.string.humidity, 62))
        listDataDetail.add(detailModel1)
        val detailModel2 = HomeModel(3, "Chỉ số UV", (11).toString())
        listDataDetail.add(detailModel2)
        val detailModel3 = HomeModel(4, "Tầm Nhìn", res.getString(R.string.visibility, 10))
        listDataDetail.add(detailModel3)
        val detailModel4 = HomeModel(5, "Điểm Sương", res.getString(R.string.dew_point, 24))
        listDataDetail.add(detailModel4)
        val detailModel5 = HomeModel(6, "Áp Suất", res.getString(R.string.pressure, 1007))
        listDataDetail.add(detailModel5)
    }

    private fun addDataEveryday() {
        val index1 = HomeModel(
            temp = res.getString(R.string.temperature_C, 28), humidity = res.getString(R.string.humidity, 15), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 16), iconWindSpeed = 1, time = res.getString(R.string.time, "10:00")
        )
        listDataEveryday.add(index1)
        val index2 = HomeModel(
            temp = res.getString(R.string.temperature_C, 24), humidity = res.getString(R.string.humidity, 25), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 15), iconWindSpeed = 1, time = res.getString(R.string.time, "11:00")
        )
        listDataEveryday.add(index2)
        val index3 = HomeModel(
            temp = res.getString(R.string.temperature_C, 27), humidity = res.getString(R.string.humidity, 37), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 19), iconWindSpeed = 1, time = res.getString(R.string.time, "12:00")
        )
        listDataEveryday.add(index3)
        val index4 = HomeModel(
            temp = res.getString(R.string.temperature_C, 19), humidity = res.getString(R.string.humidity, 40), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 10), iconWindSpeed = 1, time = res.getString(R.string.time, "13:00")
        )
        listDataEveryday.add(index4)
        val index5 = HomeModel(
            temp = res.getString(R.string.temperature_C, 28), humidity = res.getString(R.string.humidity, 15), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 16), iconWindSpeed = 1, time = res.getString(R.string.time, "14:00")
        )
        listDataEveryday.add(index5)
        val index6 = HomeModel(
            temp = res.getString(R.string.temperature_C, 24), humidity = res.getString(R.string.humidity, 25), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 15), iconWindSpeed = 1, time = res.getString(R.string.time, "15:00")
        )
        listDataEveryday.add(index6)
        val index7 = HomeModel(
            temp = res.getString(R.string.temperature_C, 27), humidity = res.getString(R.string.humidity, 37), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 19), iconWindSpeed = 1, time = res.getString(R.string.time, "16:00")
        )
        listDataEveryday.add(index7)
        val index8 = HomeModel(
            temp = res.getString(R.string.temperature_C, 19), humidity = res.getString(R.string.humidity, 40), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 10), iconWindSpeed = 1, time = res.getString(R.string.time, "17:00")
        )
        listDataEveryday.add(index8)
        val index9 = HomeModel(
            temp = res.getString(R.string.temperature_C, 27), humidity = res.getString(R.string.humidity, 37), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 19), iconWindSpeed = 1, time = res.getString(R.string.time, "18:00")
        )
        listDataEveryday.add(index9)
        val index10 = HomeModel(
            temp = res.getString(R.string.temperature_C, 19), humidity = res.getString(R.string.humidity, 40), iconStatus = 1,
            wind = res.getString(R.string.wind_speed, 10), iconWindSpeed = 1, time = res.getString(R.string.time, "19:00")
        )
        listDataEveryday.add(index10)
    }
}
package com.example.todayweather.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.todayweather.R

class DetailViewModel(application: Application) : ViewModel() {
    private val res = application.resources

    var listData = mutableListOf<DetailModel>()

    init {
        addData()
    }

    private fun addData() {
        val detailModel = DetailModel(1, "Cảm Giác Như", res.getString(R.string.temperature_C, 38))
        listData.add(detailModel)
        val detailModel1 = DetailModel(2, "Độ Ẩm", res.getString(R.string.humidity, 62))
        listData.add(detailModel1)
        val detailModel2 = DetailModel(3, "Chỉ số UV", (11).toString())
        listData.add(detailModel2)
        val detailModel3 = DetailModel(4, "Tầm Nhìn", res.getString(R.string.visibility, 10))
        listData.add(detailModel3)
        val detailModel4 = DetailModel(5, "Điểm Sương", res.getString(R.string.dew_point, 24))
        listData.add(detailModel4)
        val detailModel5 = DetailModel(6, "Áp Suất", res.getString(R.string.pressure, 1007))
        listData.add(detailModel5)
    }
}
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

    fun addData() {
        var detailModel: DetailModel = DetailModel(1, "Cảm Giác Như", res.getString(R.string.temperature_C, 38))
        listData.add(detailModel)
        var detailModel1: DetailModel = DetailModel(2, "Độ Ẩm", res.getString(R.string.humidity, 62))
        listData.add(detailModel1)
        var detailModel2: DetailModel = DetailModel(3, "Chỉ số UV", (11).toString())
        listData.add(detailModel2)
        var detailModel3: DetailModel = DetailModel(4, "Tầm Nhìn", res.getString(R.string.visibility, 10))
        listData.add(detailModel3)
        var detailModel4: DetailModel = DetailModel(5, "Điểm Sương", res.getString(R.string.dew_point, 24))
        listData.add(detailModel4)
        var detailModel5: DetailModel = DetailModel(6, "Áp Suất", res.getString(R.string.pressure, 1007))
        listData.add(detailModel5)
    }
}
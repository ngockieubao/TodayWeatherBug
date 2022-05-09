package com.example.todayweather.detail

import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    var listData = mutableListOf<DetailModel>()
    init {
        addData()
    }

    fun addData() {
        var detailModel: DetailModel = DetailModel(1, "Cảm Giác Như", 26)
        listData.add(detailModel)
        var detailModel1: DetailModel = DetailModel(2, "Độ Ẩm", 88)
        listData.add(detailModel1)
        var detailModel2: DetailModel = DetailModel(3, "Chỉ số UV", 1)
        listData.add(detailModel2)
        var detailModel3: DetailModel = DetailModel(4, "Tầm Nhìn", 10)
        listData.add(detailModel3)
        var detailModel4: DetailModel = DetailModel(5, "Điểm Sương", 23)
        listData.add(detailModel4)
        var detailModel5: DetailModel = DetailModel(6, "Áp Suất", 1011)
        listData.add(detailModel5)
    }

}
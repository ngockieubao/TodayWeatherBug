package com.example.todayweather.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayweather.R
import com.example.todayweather.database.WeatherDAO
import com.example.todayweather.home.model.Daily
import com.example.todayweather.home.model.Hourly
import com.example.todayweather.home.model.WeatherGetApi
import com.example.todayweather.network.WeatherApi
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val database: WeatherDAO, application: Application
) : ViewModel() {
    var listDataDetail = MutableLiveData<MutableList<HomeModel>>()
    var listDataHourly = MutableLiveData<MutableList<Hourly>>()

    var listDetail = mutableListOf<HomeModel>()
    var listHourly = mutableListOf<Hourly>()

    private val res = application.resources
    private val _properties = MutableLiveData<WeatherGetApi>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<WeatherGetApi>
        get() = _properties

    private fun getWeatherProperties() {
        viewModelScope.launch {
            try {
                _properties.value = WeatherApi.retrofitService.getProperties()
                addDataDetail()
                listHourly = _properties.value!!.hourly
                listDataHourly.value = listHourly
            } catch (e: Exception) {
                Log.d("bug", e.toString())
            }
        }
    }

    init {
        getWeatherProperties()
    }

    private fun addDataDetail() {
        val index1 = HomeModel(1, "Cảm Giác Như", res.getString(R.string.temperature_C, _properties.value?.current?.temp))
        listDetail.add(index1)
        val index2 = HomeModel(2, "Độ Ẩm", res.getString(R.string.humidity, _properties.value?.current?.humidity))
        listDetail.add(index2)
        val index3 = HomeModel(3, "Chỉ số UV", res.getString(R.string.uvi, _properties.value?.current?.uvi))
        listDetail.add(index3)
        val index4 = HomeModel(4, "Tầm Nhìn", res.getString(R.string.visibility, (_properties.value?.current?.visibility?.div(1000))))
        listDetail.add(index4)
        val index5 = HomeModel(5, "Điểm Sương", res.getString(R.string.dew_point, _properties.value?.current?.dew_point))
        listDetail.add(index5)
        val index6 = HomeModel(6, "Áp Suất", res.getString(R.string.pressure, _properties.value?.current?.pressure))
        listDetail.add(index6)
        listDataDetail.value = listDetail
    }
}
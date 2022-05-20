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
    // init var database
    private val database: WeatherDAO, application: Application
) : ViewModel() {
    // The external LiveData interface to the property is immutable, so only this class can modify
    // list current
    private val _listCurrent = MutableLiveData<Daily>()
    val listCurrent: LiveData<Daily>
        get() = _listCurrent

    // list data detail
    var listDataDetail = MutableLiveData<MutableList<HomeModel>>()

    // daily nav
    private val _listDailyNav = MutableLiveData<MutableList<Daily>>()
    val listDataDaily: LiveData<MutableList<Daily>>
        get() = _listDailyNav

    // hourly nav
    private val _listHourlyNav = MutableLiveData<MutableList<Hourly>>()
    val listDataHourly: LiveData<MutableList<Hourly>>
        get() = _listHourlyNav

    // var using for set data to list data detail
    private val res = application.resources

    // init var get location to show in MainActivity
    val showLocation = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getDataFromDatabase()
        }
    }

    fun getWeatherProperties(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                // get data from API
                val weatherData = WeatherApi.retrofitService.getProperties(lat, lon)
                // create and save db after get from API
                database.insert(weatherData)
                getDataFromDatabase()
            } catch (e: Exception) {
                Log.d("bug", e.toString())
            }
        }
    }

    private suspend fun getDataFromDatabase() {
        val weatherData = loadDataFromDB().lastOrNull() ?: return
        viewModelScope.launch {
            populateDailyHourlyData(weatherData)
        }
    }

    private suspend fun populateDailyHourlyData(weatherData: WeatherGetApi) {
        // display home fragment
        addDataDetail()

        // get data Daily & set to ListDaily
        val listDaily = weatherData.daily
        _listDailyNav.value = listDaily

        // get first ele of daily List
        _listCurrent.value = listDaily[0]

        // get data Hourly & set to ListHourly
        val listHourly = weatherData.hourly
        _listHourlyNav.value = listHourly
    }

    // set data to list data detail
    private suspend fun addDataDetail() {
        val listDetail = mutableListOf<HomeModel>()

        val weatherData = loadDataFromDB()
        val listCurrent = weatherData[0].current

        val index1 = HomeModel(1, res.getString(R.string.feelLikes_string), res.getString(R.string.temperature_C, listCurrent.temp))
        listDetail.add(index1)
        val index2 = HomeModel(2, res.getString(R.string.humidity_string), res.getString(R.string.humidity, listCurrent.humidity))
        listDetail.add(index2)
        val index3 = HomeModel(3, res.getString(R.string.uvi_string), res.getString(R.string.uvi, listCurrent.uvi))
        listDetail.add(index3)
        val index4 = HomeModel(
            4, res.getString(R.string.visibility_string), res.getString(R.string.visibility, (listCurrent.visibility.div(1000)))
        )
        listDetail.add(index4)
        val index5 = HomeModel(5, res.getString(R.string.dewPoint_string), res.getString(R.string.dew_point, listCurrent.dew_point))
        listDetail.add(index5)
        val index6 = HomeModel(6, res.getString(R.string.pressure_string), res.getString(R.string.pressure, listCurrent.pressure))
        listDetail.add(index6)

        // set data to observe
        listDataDetail.value = listDetail
    }

    fun showLocation(location: String) {
        showLocation.value = location
    }

    private suspend fun loadDataFromDB(): List<WeatherGetApi> {
        return database.loadAPI()
    }
}
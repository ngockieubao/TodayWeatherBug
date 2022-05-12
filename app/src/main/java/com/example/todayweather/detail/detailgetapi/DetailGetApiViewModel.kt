package com.example.todayweather.detail.detailgetapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayweather.network.WeatherApi
import kotlinx.coroutines.launch

class DetailGetApiViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    init {
        getDetailProperties()
    }

    private fun getDetailProperties() {
        viewModelScope.launch {
            try {
//                val listResult= retrofit.create(WeatherApiService::class.java).getProperties()
                val listResult = WeatherApi.retrofitService.getProperties()
                _response.value = "Success: ${listResult.toString()} Weather properties retrieved"
                Log.d("api", listResult.toString())
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
                Log.e("Exception", e.message.toString())
            }
        }
    }
}
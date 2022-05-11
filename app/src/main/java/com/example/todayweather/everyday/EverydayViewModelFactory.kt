package com.example.todayweather.everyday

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class EverydayViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EverydayViewModel::class.java)){
            return EverydayViewModel(application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}
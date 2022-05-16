package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todayweather.R
import com.example.todayweather.database.WeatherDatabase
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.databinding.FragmentNavHourlyBinding
import com.example.todayweather.home.HourlyAdapter
import com.example.todayweather.home.HourlyNavAdapter
import com.example.todayweather.home.WeatherViewModel
import com.example.todayweather.home.WeatherViewModelFactory

class HourlyFragment : Fragment() {
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var bindingHourlyNavBinding: FragmentNavHourlyBinding
    private lateinit var hourlyNavAdapter: HourlyNavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingHourlyNavBinding = FragmentNavHourlyBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        // Init ViewModel & Adapter & Database
        val dataSource = WeatherDatabase.getInstance(application).weatherDAO
        val weatherViewModelFactory = WeatherViewModelFactory(dataSource, application)
        weatherViewModel = ViewModelProvider(
            requireActivity(),
            weatherViewModelFactory
        ).get(WeatherViewModel::class.java)

        hourlyNavAdapter = HourlyNavAdapter()
        weatherViewModel.listDataHourly.observe(this.viewLifecycleOwner) {
            hourlyNavAdapter.dataList = it
        }
        bindingHourlyNavBinding.rcvNavHourly.adapter = hourlyNavAdapter

        return bindingHourlyNavBinding.root
    }
}
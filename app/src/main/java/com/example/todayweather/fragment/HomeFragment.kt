package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todayweather.R
import com.example.todayweather.database.WeatherDatabase
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.adapter.DetailAdapter
import com.example.todayweather.adapter.HourlyAdapter
import com.example.todayweather.home.WeatherViewModel
import com.example.todayweather.home.WeatherViewModelFactory
import com.example.todayweather.home.model.Hourly

class HomeFragment : Fragment() {
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var hourlyAdapter: HourlyAdapter

    private val sharedViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater)
        bindingHome.tvHomeStatusDescription.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_dailyFragment)
        }
        bindingHome.recyclerViewHourlyContainerElement.constraintHeaderHourly.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_hourlyFragment)
        }

        val application = requireNotNull(this.activity).application

        // Init ViewModel & Adapter & Database
        val dataSource = WeatherDatabase.getInstance(application).weatherDAO
        val weatherViewModelFactory = WeatherViewModelFactory(dataSource, application)
        weatherViewModel = ViewModelProvider(
            requireActivity(),
            weatherViewModelFactory
        ).get(WeatherViewModel::class.java)

        detailAdapter = DetailAdapter()
        hourlyAdapter = HourlyAdapter()

        // Set data
        weatherViewModel.listDataDetail.observe(this.viewLifecycleOwner) {
            detailAdapter.dataList = it
        }
        weatherViewModel.listDataHourly.observe(this.viewLifecycleOwner) {
            hourlyAdapter.dataList = it
        }

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter
        bindingHome.recyclerViewHourlyContainerElement.recyclerViewHourly.adapter = hourlyAdapter

        fun setDataToListHourlyNav(data: MutableList<Hourly>) {
            sharedViewModel.addListHourlyNav(data)
        }
        return bindingHome.root
    }
}
package com.example.todayweather.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todayweather.R
import com.example.todayweather.adapter.DetailAdapter
import com.example.todayweather.adapter.HourlyAdapter
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.home.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {
    private val weatherViewModel: WeatherViewModel by sharedViewModel()
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var hourlyAdapter: HourlyAdapter

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

        // Init ViewModel & Adapter & Database
//        val dataSource = WeatherDatabase.getInstance(application).weatherDAO
//        val weatherViewModelFactory = WeatherViewModelFactory(dataSource, application)
//        weatherViewModel = ViewModelProvider(
//            requireActivity(),
//            weatherViewModelFactory
//        ).get(WeatherViewModel::class.java)

        weatherViewModel.showLocation.observe(this.viewLifecycleOwner) {
            if (it != null && it != "") {
                bindingHome.tvHomeLocation.text = it
            }
        }

        weatherViewModel.listCurrent.observe(this.viewLifecycleOwner) {
            Log.d("HomeFragment", "onCreateView: $it")
            if (it != null) {
                bindingHome.item = it
            }
        }

        detailAdapter = DetailAdapter()
        hourlyAdapter = HourlyAdapter()

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter
        bindingHome.recyclerViewHourlyContainerElement.recyclerViewHourly.adapter = hourlyAdapter

        // Set data
        weatherViewModel.listDataDetail.observe(this.viewLifecycleOwner) {
            detailAdapter.dataList = it
        }
        weatherViewModel.listDataHourly.observe(this.viewLifecycleOwner) {
            hourlyAdapter.dataList = it
        }
        return bindingHome.root
    }
}

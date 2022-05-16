package com.example.todayweather.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todayweather.R
import com.example.todayweather.database.WeatherDatabase
import com.example.todayweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater)
        bindingHome.tvHomeStatusDescription.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sevenDaysFragment)
        }

        val application = requireNotNull(this.activity).application

        // Init ViewModel & Adapter & Database
        val dataSource = WeatherDatabase.getInstance(application).weatherDAO
        val weatherViewModelFactory = WeatherViewModelFactory(dataSource, application)
        weatherViewModel = ViewModelProvider(this, weatherViewModelFactory).get(WeatherViewModel::class.java)
        detailAdapter = DetailAdapter()

        // Set data
        weatherViewModel.listDataDetail.observe(this.viewLifecycleOwner) {
            detailAdapter.dataList = it
        }

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter

        return bindingHome.root
    }
}
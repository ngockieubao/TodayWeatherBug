package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todayweather.adapter.DailyNavAdapter
import com.example.todayweather.databinding.FragmentNavDailyBinding
import com.example.todayweather.home.WeatherViewModel

class DailyFragment : Fragment() {
    private lateinit var bindingDailyNavBinding: FragmentNavDailyBinding
    private lateinit var dailyNavAdapter: DailyNavAdapter

    // use share viewModel
    private val sharedViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingDailyNavBinding = FragmentNavDailyBinding.inflate(inflater, container, false)

        return bindingDailyNavBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingDailyNavBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
        }

        dailyNavAdapter = DailyNavAdapter()

        sharedViewModel.listDataDaily.observe(this.viewLifecycleOwner) {
            dailyNavAdapter.dataList = it
        }


        bindingDailyNavBinding.rcvNavDaily.adapter = dailyNavAdapter
    }
}
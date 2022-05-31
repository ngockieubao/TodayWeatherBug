package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todayweather.R
import com.example.todayweather.databinding.FragmentNavHourlyBinding
import com.example.todayweather.adapter.HourlyNavAdapter
import com.example.todayweather.home.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HourlyFragment : Fragment() {
    private lateinit var bindingHourlyNavBinding: FragmentNavHourlyBinding
    private lateinit var hourlyNavAdapter: HourlyNavAdapter

    private val sharedViewModel: WeatherViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHourlyNavBinding = FragmentNavHourlyBinding.inflate(inflater, container, false)

        bindingHourlyNavBinding.imageViewBackHourlyNav.setOnClickListener {
            findNavController().navigate(R.id.action_hourlyFragment_to_homeFragment)
        }

        return bindingHourlyNavBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingHourlyNavBinding.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // Assign the view model to a property in the binding class
            viewModel = sharedViewModel

            // use for onClick listener
//            hourlyFragment = this@HourlyFragment
        }

        hourlyNavAdapter = HourlyNavAdapter()

        sharedViewModel.listDataHourly.observe(this.viewLifecycleOwner) {
            hourlyNavAdapter.dataList = it
        }

        bindingHourlyNavBinding.rcvNavHourly.adapter = hourlyNavAdapter
    }
}
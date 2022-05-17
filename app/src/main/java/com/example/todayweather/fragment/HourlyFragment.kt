package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.todayweather.databinding.FragmentNavHourlyBinding
import com.example.todayweather.adapter.HourlyNavAdapter
import com.example.todayweather.databinding.RcvHourlyNavBinding
import com.example.todayweather.home.WeatherViewModel

class HourlyFragment : Fragment() {
    private lateinit var bindingHourlyNavBinding: FragmentNavHourlyBinding
    private lateinit var hourlyNavAdapter: HourlyNavAdapter

    private val sharedViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingHourlyNavBinding = FragmentNavHourlyBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
//
//        // Init ViewModel & Adapter & Database
//        val dataSource = WeatherDatabase.getInstance(application).weatherDAO
//        val weatherViewModelFactory = WeatherViewModelFactory(dataSource, application)
//        weatherViewModel = ViewModelProvider(
//            requireActivity(),
//            weatherViewModelFactory
//        ).get(WeatherViewModel::class.java)


        return bindingHourlyNavBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingHourlyNavBinding

        bindingHourlyNavBinding.apply {
            // Specify the fragment as the lifecycle owner
            lifecycleOwner = viewLifecycleOwner

            // Assign the view model to a property in the binding class
            viewModel = sharedViewModel

            // using for onClick listener
//            hourlyFragment = this@HourlyFragment
        }

        hourlyNavAdapter = HourlyNavAdapter()

        sharedViewModel.listDataHourly.observe(this.viewLifecycleOwner) {
            hourlyNavAdapter.dataList = it
        }

        bindingHourlyNavBinding.rcvNavHourly.adapter = hourlyNavAdapter
    }
}
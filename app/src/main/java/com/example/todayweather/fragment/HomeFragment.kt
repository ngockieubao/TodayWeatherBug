package com.example.todayweather.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todayweather.R
import com.example.todayweather.adapter.DetailAdapter
import com.example.todayweather.adapter.HourlyAdapter
import com.example.todayweather.broadcast.NotificationReceiver
import com.example.todayweather.broadcast.WeatherReceiver
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.home.WeatherViewModel
import com.example.todayweather.home.model.City
import com.example.todayweather.util.Utils
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException
import java.lang.NullPointerException

class HomeFragment : Fragment() {
    private val weatherViewModel: WeatherViewModel by sharedViewModel()
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var hourlyAdapter: HourlyAdapter

    // Init var location
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var getPosition: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var getBundle: City? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        initLocationRequest()
        initLocationCallback()

        getBundle = arguments?.getParcelable<City>("myKey")

        if (getBundle != null) {
            weatherViewModel.showLocation(getBundle!!.name)

            // Pass lat-lon args after allow position
            weatherViewModel.getWeatherProperties(getBundle!!.lat, getBundle!!.lon)
        } else {
            getLastLocation()
        }

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

        // Navigate Search Fragment
        try {
            bindingHome.imageBtnSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Log.i("fbug", ex.toString())
        }



        return bindingHome.root
    }

    // Init locationRequest
    private fun initLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    // Init locationCallback
    private fun initLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // Get lat-lon
                    val latUpdate = location.latitude
                    val lonUpdate = location.longitude

                    // Get location
                    getLocation(latUpdate, lonUpdate)
                }
            }
        }
    }

    // Check permission location & Get location
    private fun getLastLocation() {
        // Get last location
        try {
            fusedLocationClient!!.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        val latUpdate = location.latitude
                        val lonUpdate = location.longitude
                        getLocation(latUpdate, lonUpdate)
                    } else {
                        startLocationUpdates()
                    }
                }.addOnFailureListener {
                    Log.e("addOnFailureListener", "getLastLocation: ${it.message}")
                }.addOnCompleteListener {
                    Log.i("addOnCompleteListener", "Completed!")
                }
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    // Get location
    private fun getLocation(lat: Double, lon: Double) {
        try {
            val geocoder = Geocoder(requireContext())
            val position = geocoder.getFromLocation(lat, lon, 1)

            // Display location
            getPosition = position[0].getAddressLine(0)
            weatherViewModel.showLocation(Utils.formatLocation(getPosition))

            // Pass lat-lon args after allow position
            weatherViewModel.getWeatherProperties(lat, lon)
        } catch (ex: IOException) {
            Log.d("bugUpdateLocation", ex.toString())
        }
    }

    // Update location
    private fun startLocationUpdates() {
        try {
            fusedLocationClient!!.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Log.e("SecurityException", e.toString())
        }
    }
}

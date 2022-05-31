package com.example.todayweather.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
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
import com.example.todayweather.home.model.City
import com.example.todayweather.util.Constants
import com.example.todayweather.util.Utils
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException

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

//        val imgSetting = bindingHome.imageBtnSetting
//        showPopup(imgSetting)
//        showMenu(imgSetting)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        initLocationRequest()
        initLocationCallback()

        getBundle = arguments?.getParcelable<City>(Constants.KEY_BUNDLE_SELECT_CITY)
        bindingHome.imageBtnSetting.setOnClickListener {
            val popupMenu = PopupMenu(requireActivity(), bindingHome.imageBtnSearch)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.convert_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked
                Toast.makeText(requireActivity(), "You Clicked " + menuItem.title, Toast.LENGTH_SHORT).show()

                true
            }
            // Showing the popup menu
            popupMenu.show()
        }

        getBundle = arguments?.getParcelable(Constants.KEY_BUNDLE_SELECT_CITY)

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
        }

        return bindingHome.root
    }

//    private fun showPopup(v: View) {
//        val popup = PopupMenu(requireActivity(), v)
//        val inflater: MenuInflater = popup.menuInflater
//        inflater.inflate(R.menu.convert_menu, popup.menu)
//        popup.show()
//    }
//
//    private fun showMenu(v: View) {
//        PopupMenu(requireActivity(), v).apply {
//            // MainActivity implements OnMenuItemClickListener
//            setOnMenuItemClickListener(this@HomeFragment)
//            inflate(R.menu.convert_menu)
//            show()
//        }
//    }
//
//    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        return when (item!!.itemId) {
//            R.id.menu_convert_celsius -> {
//                switchToCelsiusMode()
//                true
//            }
//            R.id.menu_convert_fahrenheit -> {
//                switchToFahrenheitMode()
//                true
//            }
//            else -> false
//        }
//    }
//
//    private fun switchToFahrenheitMode() {
//
//    }
//
//    private fun switchToCelsiusMode() {
//    }

    // Init locationRequest
    private fun initLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = Constants.INTERVAL
            fastestInterval = Constants.FASTEST_INTERVAL
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
                    }.addOnCompleteListener {}
            } catch (ex: SecurityException) {
                ex.printStackTrace()
            }
        } catch (ex: NullPointerException) {
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
            weatherViewModel.showLocation(Utils.formatLocation(requireContext(), getPosition))

            // Pass lat-lon args after allow position
            weatherViewModel.getWeatherProperties(lat, lon)
        } catch (ex: IOException) {
            ex.printStackTrace()
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
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            // Use the Builder class for convenient dialog construction
//            val builder = AlertDialog.Builder(it)
//            builder.setMessage(R.string.dialog_settings)
//                .setPositiveButton(R.string.start,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        // START THE GAME!
//                    })
//                .setNegativeButton(R.string.cancel,
//                    DialogInterface.OnClickListener { dialog, id ->
//                        // User cancelled the dialog
//                    })
//            // Create the AlertDialog object and return it
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
    }

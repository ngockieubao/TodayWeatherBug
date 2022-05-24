package com.example.todayweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.todayweather.home.WeatherViewModel
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModel()

    // init var location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var getPosition: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.actNavHost) as NavHostFragment
//        val navController = navHostFragment.navController

//        val navController = this.findNavController(R.id.actNavHost)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        // init var use for get location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        initLocationRequest()
        initLocationCallback()
        startLocationUpdates()
    }

    // init locationRequest
    private fun initLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // get lat-lon
                    val latUpdate = location.latitude
                    val lonUpdate = location.longitude

                    // pass lat-lon args after allow position
                    weatherViewModel.getWeatherProperties(latUpdate, lonUpdate)

                    // init var for update location
                    try {
                        val geocoder = Geocoder(this@MainActivity)
                        val position = geocoder.getFromLocation(latUpdate, lonUpdate, 1)
                        Log.d("location", position[0].toString())
                        // display location
                        getPosition = position[0].getAddressLine(0)
                        weatherViewModel.showLocation(getPosition)
                    } catch (ex: IOException) {
                        Log.d("bugUpdateLocation", ex.toString())
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.actNavHost)
        return navController.navigateUp()
    }

    // init locationCallback
    private fun initLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    // get last location
    private val localPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        // permission = it : result of return
            permission ->
        if (permission == true) {
            getLastLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            // navigate to setup permission
        }
    }

    // show dialog to request permission location
    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Run secondly show dialog with 3 choices
                localPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            } else {
                // Run firstly show dialog with 2 choices
                localPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        } else {
            TODO("VERSION.SDK_INT < M")
        }
    }

    // check permission location & Get location
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Get location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        val lat = location.latitude
                        val lon = location.longitude
                        try {
                            val geocoder = Geocoder(this)
                            val position = geocoder.getFromLocation(lat, lon, 1)
                            Log.d("location", position[0].toString())
                            getPosition = position[0].getAddressLine(0)
                            weatherViewModel.getWeatherProperties(lat, lon)
                            weatherViewModel.showLocation(getPosition)
                        } catch (e: Exception) {
                            Log.w("bugLocation", e)
                        }
                    } else {
                        startLocationUpdates()
                    }
                }.addOnFailureListener {
                    Log.e("addOnFailureListener", "getLastLocation: ${it.message}")
                }.addOnCompleteListener {
                    Log.i("addOnCompleteListener", "Completed!")
                }
            return
        } else {
            requestLocationPermission()
        }
    }

    // update location
    private fun startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Log.e("SecurityException", e.toString())
        }
    }
}
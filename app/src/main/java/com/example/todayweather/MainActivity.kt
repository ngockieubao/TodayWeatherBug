package com.example.todayweather

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.todayweather.broadcast.NotificationReceiver
import com.example.todayweather.broadcast.WeatherReceiver
import com.example.todayweather.home.WeatherViewModel
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModel()

    // Init var location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var getPosition: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.actNavHost) as NavHostFragment
//        val navController = navHostFragment.navController

//        val navController = this.findNavController(R.id.actNavHost)
//        NavigationUI.setupActionBarWithNavController(this, navController)

        // Init var use for get location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLastLocation()
        initLocationRequest()
        initLocationCallback()
        startLocationUpdates()
        startBrR()
        startBroadcast()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.actNavHost)
        return navController.navigateUp()
    }

    // Init locationRequest
    private fun initLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // Get lat-lon
                    val latUpdate = location.latitude
                    val lonUpdate = location.longitude

                    // Pass lat-lon args after allow position
                    weatherViewModel.getWeatherProperties(latUpdate, lonUpdate)

                    // Init var for update location
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

    // Init locationCallback
    private fun initLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    // Get last location
    private val localPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        // permission = it : result of return
            permission ->
        if (permission == true) {
            getLastLocation()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            // Navigate to setup permission
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

    // Update location
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

    // Broadcast
    private fun startBrR() {
        val intent = Intent(this, WeatherReceiver::class.java).apply {
            action = "com.example.todayweather.NetworkReceiver"
        }
        sendBroadcast(intent)
    }

    private fun startBroadcast() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            action = "com.example.todayweather.AlarmManager"
        }
        sendBroadcast(intent)

//        val pendingIntentRequestCode = 0
//        // Cannot push notification when using pendingIntent
//        // Todo fix later
//
//        val pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val isPermission = alarmManager.canScheduleExactAlarms()
//            if (isPermission) {
//                alarmManager.setInexactRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + 10_000,
//                    TimeUnit.SECONDS.toMillis(10),
//                    pendingIntent
//                )
//            } else {
//                alarmManager.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    System.currentTimeMillis() + 10_000,
//                    TimeUnit.SECONDS.toMillis(10),
//                    pendingIntent
//                )
//            }
//        }
    }
}
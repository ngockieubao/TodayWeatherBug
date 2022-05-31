package com.example.todayweather

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.todayweather.broadcast.NotificationReceiver
import com.example.todayweather.broadcast.WeatherReceiver
import com.example.todayweather.databinding.ActivityMainBinding
import com.example.todayweather.home.WeatherViewModel
import com.example.todayweather.util.Constants
import com.example.todayweather.util.Utils
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    // Init var location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var getPosition: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Init var use for get location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initLocationRequest()
        initLocationCallback()
        checkPermissionLocation()
    }

    // Navigation by nav host
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.actNavHost)
        return navController.navigateUp()
    }

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

    // Get last location
    private val localPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        // permission = it : result of return
            permission ->
        if (permission == true) {
            startBroadcast()
        } else {
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
//            TODO Navigate to setup permission
//            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//            val uri: Uri = Uri.fromParts(Constants.PACKAGE, packageName, null)
//            intent.data = uri
//            startActivity(intent)
        }
    }

    // Show dialog to request permission location
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

    // Check permission location & Get location
    private fun checkPermissionLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Get last location
            startBroadcast()
        } else {
            requestLocationPermission()
        }
    }

    private fun startBroadcast() {
        startBroadcastNetwork()
        startBroadcastWeatherNotifications()
    }

    // Get location
    private fun getLocation(lat: Double, lon: Double) {
        try {
            val geocoder = Geocoder(this@MainActivity)
            val position = geocoder.getFromLocation(lat, lon, 1)

            // Display location
            getPosition = position[0].getAddressLine(0)
            weatherViewModel.showLocation(Utils.formatLocation(this, getPosition))

            // Pass lat-lon args after allow permission
            weatherViewModel.getWeatherProperties(lat, lon)
        } catch (ex: IOException) {
            ex.printStackTrace()
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
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    // Broadcast
    private fun startBroadcastNetwork() {
        val intent = Intent(this, WeatherReceiver::class.java).apply {
            action = Constants.BROADCAST_RECEIVER_NETWORK_STATUS
        }
        sendBroadcast(intent)
    }

    private fun startBroadcastWeatherNotifications() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            action = Constants.BROADCAST_RECEIVER_PUSH_NOTIFICATIONS
        }

        val pendingIntentRequestCode = 0
        val pendingIntent = PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val isPermission = alarmManager.canScheduleExactAlarms()
            if (isPermission) {
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 1_000,
                    TimeUnit.SECONDS.toMillis(10),
                    pendingIntent
                )
            } else {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + 60_000,
                    TimeUnit.SECONDS.toMillis(10),
                    pendingIntent
                )
            }
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 60_000,
                TimeUnit.SECONDS.toMillis(10),
                pendingIntent
            )
        }
    }
}
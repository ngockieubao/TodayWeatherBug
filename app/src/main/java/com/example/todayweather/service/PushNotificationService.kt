package com.example.todayweather.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.todayweather.R
import com.example.todayweather.home.model.WeatherGetApi
import com.example.todayweather.network.WeatherApiService
import com.example.todayweather.util.Utils
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.io.IOException

class PushNotificationService : Service() {
    private val weatherApiService: WeatherApiService by inject()
    private val ONGOING_NOTIFICATION_ID = 2
    private var getPosition: String = ""
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var job: Job = Job()
    private val coroutineContext = CoroutineScope(Dispatchers.Main + job)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val TAG_SERVICE = "HelloService"
    private var isRunning = false

    override fun onBind(intent: Intent?): IBinder? {
        Log.i(TAG_SERVICE, "Service onBind")
        return null
    }

    override fun onCreate() {
        isRunning = true
        Log.i(TAG_SERVICE, "Service onCreate")
        initLocationRequest()
        initLocationCallback()
    }

    override fun onDestroy() {
        isRunning = false
        job.cancel() // cancel the Job
        Log.i(TAG_SERVICE, "Service onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG_SERVICE, "Service onStartCommand")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.name_notification_channel)
            val descriptionText = getString(R.string.description_notification_channel)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(getString(R.string.id_channel_push_notification), name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
        startPushNotification(startId)

        /**
         * Todo: call weather API with Database' latitude and longitude
         * then push notification with weather information
         */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        when (PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
            }
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                coroutineContext.launch {
                    val lat: Double
                    val lon: Double
                    if (location != null) {
                        lat = location.latitude
                        lon = location.longitude
                        val weatherData = weatherApiService.getProperties(lat, lon)

                        // Get location
                        getLocation(lat, lon)

                        // Push Weather Notification
                        startPushNotificationShowInfo(weatherData, getPosition)
                        delay(2000)
                        stopSelf(startId)
                    } else {
                        startLocationUpdates()
                    }
                }
            }
        return START_STICKY
    }

    private fun startPushNotification(startId: Int) {
        // Create the notification to be shown
        val mBuilder: Notification = NotificationCompat.Builder(this@PushNotificationService, getString(R.string.id_channel_push_notification))
            .setSmallIcon(R.mipmap.ic_todayweather_removebg)
            .setLargeIcon(Utils.convertToBitMap(this@PushNotificationService, R.mipmap.ic_weather_news))
            .setAutoCancel(true)
            .setContentText(getString(R.string.notification_waiting_get_weather_infomation))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        startForeground(startId, mBuilder)
    }

    @SuppressLint("StringFormatMatches")
    private fun startPushNotificationShowInfo(weatherGetApi: WeatherGetApi, location: String) {
        // Create the notification to be shown
        val mBuilder = NotificationCompat.Builder(this@PushNotificationService, getString(R.string.id_channel_push_notification))
            .setSmallIcon(R.mipmap.ic_todayweather_removebg)
            .setLargeIcon(Utils.convertToBitMap(this@PushNotificationService, R.mipmap.ic_weather_news))
            .setAutoCancel(true)
            .setContentTitle(Utils.formatLocation(location))
            .setContentText(Utils.upCaseFirstLetter(weatherGetApi.current.weather[0].description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat
                    .BigTextStyle()
                    .bigText(
                        resources.getString(
                            R.string.fm_status_daily_nav,
                            Utils.upCaseFirstLetter(weatherGetApi.current.weather[0].description),
                            weatherGetApi.daily[0].temp.max,
                            weatherGetApi.daily[0].temp.min,
                            Utils.formatWindDeg(this@PushNotificationService, weatherGetApi.daily[0].wind_deg),
                            weatherGetApi.daily[0].wind_speed,
                            weatherGetApi.daily[0].pop
                        )
                    )
            )

        val am = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        am.notify(ONGOING_NOTIFICATION_ID, mBuilder.build())
    }

    // Init locationCallback
    private fun initLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

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

    // Get location
    private fun getLocation(latUpdate: Double, lonUpdate: Double) {
        try {
            val geocoder = Geocoder(this@PushNotificationService)
            val position = geocoder.getFromLocation(latUpdate, lonUpdate, 1)
            Log.d("location", position[0].toString())
            // display location
            getPosition = position[0].getAddressLine(0)
        } catch (ex: IOException) {
            Log.d("bugUpdateLocation", ex.toString())
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
}



package com.example.todayweather.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
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

class TestService : Service() {
    private val ONGOING_NOTIFICATION_ID = 1
    private val weatherApiService: WeatherApiService by inject()

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
//        startBroadcast()
        Log.i(TAG_SERVICE, "Service onCreate")
    }

    override fun onDestroy() {
        isRunning = false
        job.cancel() // cancel the Job
        Log.i(TAG_SERVICE, "Service onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startPushNotification()

        Log.i(TAG_SERVICE, "Service onStartCommand")

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
                        Log.i("service", weatherData.toString())
                        // push Weather Notification

                        stopSelf(startId)
                    }
                }
            }

        return START_STICKY
    }

    private fun startPushNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "Alarm"
            val descriptionText = "Alarm details"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("AlarmId", name, importance)
            mChannel.description = descriptionText
            val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }

        // Create the notification to be shown
        val mBuilder: Notification = NotificationCompat.Builder(this@TestService, "AlarmId")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setContentText("Đang lấy thông tin thời tiết")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(ONGOING_NOTIFICATION_ID, mBuilder)

    }
}



package com.example.todayweather.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

class WeatherReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "com.example.todayweather.NetworkReceiver") {
            isOnline(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    Toast.makeText(context, "Đã kết nối Internet", Toast.LENGTH_SHORT).show()
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    Toast.makeText(context, "Đã kết nối Internet", Toast.LENGTH_SHORT).show()
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    Toast.makeText(context, "Đã kết nối Internet", Toast.LENGTH_SHORT).show()
                    return true
                }
            } else {
                Toast.makeText(context, "Bạn đang ngoại tuyến", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun alarmWeather(context: Context) {
//        val alarmManager =
//            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, WeatherReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
    }
}

package com.example.todayweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todayweather.home.model.WeatherGetApi
import com.example.todayweather.network.Converters

@Database(entities = [WeatherGetApi::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDAO: WeatherDAO

//    companion object {
//        @Volatile
//        private var INSTANCE: WeatherDatabase? = null
//        fun getInstance(context: Context): WeatherDatabase {
//            synchronized(Database::class.java) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                           WeatherDatabase::class.java,
//                        "database"
//                    ).build()
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}
package com.example.todayweather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todayweather.home.model.WeatherGetApi

@Dao
interface WeatherDAO {
//    @Insert
//    fun insert(weatherGetApi: List<WeatherGetApi>)
//
//    @Update
//    fun update(weatherGetApi: WeatherGetApi)
//
////    @Query("SELECT * from detail_get_api_table WHERE nightId = :key")
////    fun get(key: Long): DetailGetApi?
//
//    @Query("DELETE FROM get_api_table")
//    fun clear()
}
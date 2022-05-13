package com.example.todayweather.detailgetapi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todayweather.home.data.*

@Entity(tableName = "detail_get_api_table")
data class DetailGetApi(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "base")
    val base: String,

    @ColumnInfo(name = "clouds")
    val clouds: Clouds,

    @ColumnInfo(name = "cod")
    val cod: Int,

    @ColumnInfo(name = "coord")
    val coord: Coord,

    @ColumnInfo(name = "dt")
    val dt: Int,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "main")
    val main: Main,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "sys")
    val sys: Sys,

    @ColumnInfo(name = "timezone")
    val timezone: Int,

    @ColumnInfo(name = "visibility")
    val visibility: Int,

    @ColumnInfo(name = "weather")
    val weather: List<Weather>,

    @ColumnInfo(name = "wind")
    val wind: Wind
)
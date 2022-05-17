package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvDailyNavBinding
import com.example.todayweather.home.model.Daily
import com.example.todayweather.home.model.Temp
import java.text.SimpleDateFormat
import java.util.*

class DailyNavAdapter : RecyclerView.Adapter<DailyNavAdapter.DailyNavViewHolder>() {

    // init list data
    var dataList = listOf<Daily>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create ViewHolder with args are binding layout
    class DailyNavViewHolder(private val rcvNavDailyBinding: RcvDailyNavBinding) :
        RecyclerView.ViewHolder(rcvNavDailyBinding.root) {

        fun bind(item: Daily?) {
            if (item == null) return

            rcvNavDailyBinding.imgViewDailyNavIcon.setImageResource(
                when (item.icon) {
                    1 -> R.mipmap.ic_water
                    2 -> R.mipmap.ic_sun
                    else -> R.mipmap.ic_cloud
                }
            )

            // Format data
            // humidity
            val humidity = item.humidity
            val humidityFormat = "$humidity%"
            if (humidity != 0)
                rcvNavDailyBinding.tvDailyNavHumidity.text = humidityFormat
            else
                rcvNavDailyBinding.tvDailyNavHumidity.text = " "

            // date
            val getDate = item.dt
            val date = Date(getDate * 1000)
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault())
            var dayOfWeekFormat = dayOfWeek.format(date)
            dayOfWeekFormat = when (dayOfWeekFormat) {
                "Monday" -> "THỨ HAI"
                "Tuesday" -> "THỨ BA"
                "Wednesday" -> "THỨ TƯ"
                "Thursday" -> "THỨ NĂM"
                "Friday" -> "THỨ SÁU"
                "Saturday" -> "THỨ BẢY"
                "Sunday" -> "CHỦ NHẬT"
                else
                -> "BUG!!!"
            }
            val dayMonth = SimpleDateFormat("dd 'THÁNG' M", Locale.getDefault())
            val dayMonthFormat = dayMonth.format(date)
            val dateFormat = "$dayOfWeekFormat, $dayMonthFormat"
            rcvNavDailyBinding.tvDailyNavDate.text = dateFormat.format(date)

            // temperature
            val getTemp: Temp = item.temp
            val tempMax = String.format("%.0f", getTemp.max)
            val tempMin = String.format("%.0f", getTemp.min)
            val tempFormat = "$tempMax° / $tempMin°"
            rcvNavDailyBinding.tvDailyNavCelcius.text = tempFormat

            // status
            val description = item.weather[0].description
            rcvNavDailyBinding.tvDailyNavStatus.text = description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyNavViewHolder {
        return DailyNavViewHolder(
            RcvDailyNavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DailyNavViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
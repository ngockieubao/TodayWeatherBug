package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvHourlyNavBinding
import com.example.todayweather.home.model.Hourly
import java.text.SimpleDateFormat
import java.util.*

class HourlyNavAdapter : RecyclerView.Adapter<HourlyNavAdapter.HourlyNavViewHolder>() {

    // init list data
    var dataList = listOf<Hourly>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create ViewHolder with args are binding layout
    class HourlyNavViewHolder(private val rcvNavHourlyBinding: RcvHourlyNavBinding) :
        RecyclerView.ViewHolder(rcvNavHourlyBinding.root) {

        fun bind(item: Hourly?) {
            if (item == null) return
            rcvNavHourlyBinding.imgViewHourlyNavIcon.setImageResource(
                when (item.icon) {
                    1 -> R.mipmap.ic_water
                    2 -> R.mipmap.ic_sun
                    else -> R.mipmap.ic_cloud
                }
            )

            // Format data
            // humidity
            val pop = item.pop * 100
            val popFormat = String.format("%.0f%%", pop)
            if (pop != 0.0)
                rcvNavHourlyBinding.tvHourlyNavHumidity.text = popFormat
            else
                rcvNavHourlyBinding.tvHourlyNavHumidity.text = " "

            // date
            val getDate = item.dt
            val time = Date(getDate * 1000)
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            rcvNavHourlyBinding.tvHourlyNavTime.text = timeFormat.format(time)

            // temp
            val temp = item.temp
            val feelsLike = item.feels_like
            val tempFormat = String.format("%.0f", temp)
            val feelsLikeFormat = String.format("%.0f", feelsLike)
            val tempFeelsLike = "$tempFormat°C - Cảm Giác Như: $feelsLikeFormat°C"
            rcvNavHourlyBinding.tvHourlyNavTemp.text = tempFeelsLike

            // status description
            val statusAbove = item.weather[0].description
            rcvNavHourlyBinding.tvHourlyNavStatusAbove.text = statusAbove
            val statusBelowWind = item.wind_speed
            val statusBelowWindDeg = item.wind_deg
            val statusBelowWindFormat = String.format("Gió: %.0fkm/h •", statusBelowWind)
            rcvNavHourlyBinding.tvHourlyNavStatusBelow.text = statusBelowWindFormat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyNavViewHolder {
        return HourlyNavViewHolder(
            RcvHourlyNavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyNavViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
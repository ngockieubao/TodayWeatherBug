package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvHourlyNavBinding
import com.example.todayweather.home.model.Hourly
import com.example.todayweather.util.Utils

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
//            rcvNavHourlyBinding.imgViewHourlyNavIcon.setImageResource(
//                when (item.icon) {
//                    1 -> R.mipmap.ic_water
//                    2 -> R.mipmap.ic_sun
//                    else -> R.mipmap.ic_cloud
//                }
//            )
            Glide.with(rcvNavHourlyBinding.imgViewHourlyNavIcon).load("http://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png")
                .into(rcvNavHourlyBinding.imgViewHourlyNavIcon)

            // pop
            val getPop = item.pop
            rcvNavHourlyBinding.tvHourlyNavHumidity.text = Utils.formatPop(getPop)

            // time
            val getTime = item.dt
            rcvNavHourlyBinding.tvHourlyNavTime.text = Utils.formatTime(getTime)

            // temp
            val temp = item.temp
            val feelsLike = item.feels_like
            rcvNavHourlyBinding.tvHourlyNavTemp.text = Utils.formatTempFeelsLike(temp, feelsLike)

            // status description
            val statusAbove = item.weather[0].description
            rcvNavHourlyBinding.tvHourlyNavStatusAbove.text = statusAbove
            val statusBelowWind = item.wind_speed
            val statusBelowWindDeg = item.wind_deg
            val statusBelowWindFormat = Utils.formatWind(statusBelowWind)
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
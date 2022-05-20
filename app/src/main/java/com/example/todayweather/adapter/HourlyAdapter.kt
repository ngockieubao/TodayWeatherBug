package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvHourlyElementBinding
import com.example.todayweather.home.model.Hourly

class HourlyAdapter : RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {

    // init list data
    var dataList = listOf<Hourly>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create ViewHolder with args are binding layout
    class HourlyViewHolder(private val rcvHourlyElementBinding: RcvHourlyElementBinding) :
        RecyclerView.ViewHolder(rcvHourlyElementBinding.root) {

        fun bind(item: Hourly?) {
            if (item == null) return
            rcvHourlyElementBinding.item = item

            // Load icon
            Glide.with(rcvHourlyElementBinding.rcvHourlyImgViewIcStatus).load("http://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png")
                .into(rcvHourlyElementBinding.rcvHourlyImgViewIcStatus)

            rcvHourlyElementBinding.rcvHourlyImgViewIcWindSpeed.setImageResource(
                when (item.icon) {
                    1 -> R.mipmap.ic_cloud
                    2 -> R.mipmap.ic_sun
                    else -> R.mipmap.ic_cursor
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        return HourlyViewHolder(
            RcvHourlyElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
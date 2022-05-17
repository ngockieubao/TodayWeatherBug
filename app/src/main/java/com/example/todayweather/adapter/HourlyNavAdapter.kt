package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvHourlyNavBinding
import com.example.todayweather.home.model.Hourly

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
                when(item.icon){
                    1 -> R.mipmap.ic_cloud
                    2 -> R.mipmap.ic_sun
                    else -> R.mipmap.ic_water
                }
            )
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
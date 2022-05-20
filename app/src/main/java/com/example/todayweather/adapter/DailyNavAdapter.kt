package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.R
import com.example.todayweather.databinding.RcvDailyNavBinding
import com.example.todayweather.home.model.Daily
import com.example.todayweather.home.model.Temp
import com.example.todayweather.util.Utils

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

            // humidity
            val getPop = item.pop
            if (getPop != 0.0)
                rcvNavDailyBinding.tvDailyNavHumidity.text = Utils.formatPop(getPop)
            else
                rcvNavDailyBinding.tvDailyNavHumidity.text = " "

            // date
            val getDate = item.dt
            rcvNavDailyBinding.tvDailyNavDate.text = Utils.formatDate(getDate)

            // temperature
            val getTemp: Temp = item.temp
            val tempMax = getTemp.max
            val tempMin = getTemp.min
            rcvNavDailyBinding.tvDailyNavCelcius.text = Utils.formatTempMaxMin(tempMax, tempMin)

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
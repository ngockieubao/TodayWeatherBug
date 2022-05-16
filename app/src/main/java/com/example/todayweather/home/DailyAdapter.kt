//package com.example.todayweather.home
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.todayweather.R
//import com.example.todayweather.databinding.RcvHourlyElementBinding
//import com.example.todayweather.home.model.Daily
//import com.example.todayweather.home.model.Hourly
//
//class DailyAdapter : RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
//
//    // init list data
//    var dataList = listOf<Daily>()
//        @SuppressLint("NotifyDataSetChanged")
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    // create ViewHolder with args are binding layout
//    class DailyViewHolder(private val rcvDailyElementBinding: RcvHourlyElementBinding) :
//        RecyclerView.ViewHolder(rcvDailyElementBinding.root) {
//
//        fun bind(item: Daily?) {
//            if (item == null) return
//            rcvDailyElementBinding.item = item
//            rcvDailyElementBinding.rcvHourlyImgViewIcStatus.setImageResource(
//                when(item.icon){
//                    1 -> R.mipmap.ic_cloud
//                    2 -> R.mipmap.ic_sun
//                    else -> R.mipmap.ic_water
//                }
//            )
//            rcvHourlyElementBinding.rcvHourlyImgViewIcWindSpeed.setImageResource(
//                when(item.icon){
//                    1 -> R.mipmap.ic_cloud
//                    2 -> R.mipmap.ic_sun
//                    else -> R.mipmap.ic_water
//                }
//            )
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
//        return DailyViewHolder(
//            RcvHourlyElementBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
//        holder.bind(dataList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//}
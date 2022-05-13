//package com.example.todayweather.home
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.todayweather.R
//import com.example.todayweather.databinding.RcvEverydayElementBinding
//
//class DailyAdapter : RecyclerView.Adapter<DailyAdapter.EverydayViewHolder>() {
//
//    // init list data
//    var dataList = listOf<HomeModel>()
//        @SuppressLint("NotifyDataSetChanged")
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    class EverydayViewHolder(private val rcvEverydayElementBinding: RcvEverydayElementBinding) :
//        RecyclerView.ViewHolder(rcvEverydayElementBinding.root) {
//
//        fun bind(item: HomeModel?) {
//            if (item == null) return
//
//            rcvEverydayElementBinding.tvRecyclerViewEverydayTemp.text = item.temp
//            rcvEverydayElementBinding.tvRecyclerViewEverydayHumidity.text = item.humidity
//            rcvEverydayElementBinding.imgViewIcStatusHomeRcvEveryday.setImageResource(
//                when (item.iconStatus) {
//                    1 -> R.mipmap.ic_cloud
//                    else -> R.mipmap.ic_cloud
//                }
//            )
//            rcvEverydayElementBinding.tvRecyclerViewEverydayWindSpeed.text = item.wind
//            rcvEverydayElementBinding.imgViewIcWindSpeedHomeRcvEveryday.setImageResource(
//                when (item.iconWindSpeed) {
//                    1 -> R.mipmap.ic_dew_point
//                    else -> R.mipmap.ic_dew_point
//                }
//            )
//            rcvEverydayElementBinding.tvRecyclerViewEverydayTime.text = item.time
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverydayViewHolder {
//        return EverydayViewHolder(
//            RcvEverydayElementBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: EverydayViewHolder, position: Int) {
//        holder.bind(dataList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//}
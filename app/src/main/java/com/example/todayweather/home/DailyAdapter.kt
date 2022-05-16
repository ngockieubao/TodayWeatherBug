//package com.example.todayweather.home
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.todayweather.R
//import com.example.todayweather.databinding.RcvDailyBinding
//import com.example.todayweather.home.model.Daily
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
//    class DailyViewHolder(private val rcvDailyBinding: RcvDailyBinding) :
//        RecyclerView.ViewHolder(rcvDailyBinding.root) {
//
//        fun bind(item: Daily?) {
//            if (item == null) return
//
////            rcvDailyBinding.imageViewDaily.setImageDrawable(
////                when(item.icon){
////                    1 -> R.mipmap.ic_cloud
////                    2 -> R.mipmap.ic_sun
////                    else -> R.mipmap.ic_water
////                }
////
//            rcvDailyBinding.item = item
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
//        return DailyViewHolder(
//            RcvDailyBinding.inflate(
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
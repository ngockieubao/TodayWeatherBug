package com.example.todayweather.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.databinding.RecyclerviewDetailElementBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    // init list data
    var dataList = listOf<DetailModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // create ViewHolder with args are binding layout
    class DetailViewHolder(val recyclerviewDetailElementBinding: RecyclerviewDetailElementBinding) :
        RecyclerView.ViewHolder(recyclerviewDetailElementBinding.root) {
//        val imgView_ic_detail: ImageView
//        val tv_desc_detail: TextView
//        val tv_res_detail: TextView
//
//        init {
//            // Define click listener for the ViewHolder's View.
//            imgView_ic_detail = recyclerviewDetailElementBinding.imgViewIcHomeDetail
//            tv_desc_detail = recyclerviewDetailElementBinding.tvTitleHomeDetail
//            tv_res_detail = recyclerviewDetailElementBinding.tvResHomeDetail
//        }

        fun bind(item: DetailModel?) {
            if (item == null) return
            recyclerviewDetailElementBinding.item = item

//            holder.qualityImage.setImageResource(when (item.sleepQuality) {
//                0 -> R.drawable.ic_sleep_0
//                1 -> R.drawable.ic_sleep_1
//                2 -> R.drawable.ic_sleep_2
//                3 -> R.drawable.ic_sleep_3
//                4 -> R.drawable.ic_sleep_4
//                5 -> R.drawable.ic_sleep_5
//                else -> R.drawable.ic_sleep_active
//            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val detailViewHolder = DetailViewHolder(
            RecyclerviewDetailElementBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
        return detailViewHolder
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(dataList.get(position))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}
package com.example.todayweather.everyday

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.databinding.RcvEverydayElementBinding

class EverydayAdapter : RecyclerView.Adapter<EverydayAdapter.EverydayViewHoler>() {

    // init list data
    var dataList = listOf<EverydayModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class EverydayViewHoler(private val everydayElementBinding: RcvEverydayElementBinding) :
        RecyclerView.ViewHolder(everydayElementBinding.root) {

        fun bind(item: EverydayModel?) {
            if (item == null) return

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverydayViewHoler {
        return EverydayViewHoler(
            RcvEverydayElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EverydayViewHoler, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

//class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

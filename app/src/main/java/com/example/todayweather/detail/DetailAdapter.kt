package com.example.todayweather.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.databinding.RecyclerviewDetailElementBinding

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    var data = listOf<DetailModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class DetailViewHolder(recyclerviewDetailElementBinding: RecyclerviewDetailElementBinding) :
        RecyclerView.ViewHolder(recyclerviewDetailElementBinding.root) {
            
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
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
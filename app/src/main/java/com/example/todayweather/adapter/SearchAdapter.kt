package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.databinding.RcvSearchElementBinding
import com.example.todayweather.home.model.City

class SearchAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listCities = listOf<City>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CityHolder(var viewBinding: RcvSearchElementBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RcvSearchElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cityHolder = holder as CityHolder
        cityHolder.viewBinding.selectCityText.text = listCities[position].city
    }

    override fun getItemCount(): Int {
        return listCities.size
    }
}
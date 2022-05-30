package com.example.todayweather.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.databinding.RcvSearchElementBinding
import com.example.todayweather.fragment.SelectCity
import com.example.todayweather.home.model.City

class SearchAdapter(
    private val context: Context,
    private val selectCity: SelectCity
) : RecyclerView.Adapter<SearchAdapter.CityHolder>() {
    var listCities = listOf<City>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CityHolder(private val viewBinding: RcvSearchElementBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: City?) {
            if (item == null) return
            viewBinding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = RcvSearchElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityHolder(binding)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(listCities[position])

        // Set onClick to each item
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            selectCity.selectItem(listCities[position])
        }
    }

    override fun getItemCount(): Int {
        return listCities.size
    }
}
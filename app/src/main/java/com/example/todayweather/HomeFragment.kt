package com.example.todayweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.detail.DetailAdapter
import com.example.todayweather.detail.DetailViewModel

class HomeFragment : Fragment() {
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater)
        bindingHome.tvHomeStatusDescription.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sevenDaysFragment)
        }

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailAdapter = DetailAdapter()

        val layoutManager = GridLayoutManager(context, 3)

        // Set data
        detailAdapter.dataList = detailViewModel.listData

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter

        // Set layout
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.layoutManager = layoutManager


//        bindingReDetail.recyclerViewDetail.adapter = detailAdapter

        return bindingHome.root
    }
}
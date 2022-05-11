package com.example.todayweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.detail.DetailAdapter
import com.example.todayweather.detail.DetailViewModel
import com.example.todayweather.detail.DetailViewModelFactory
import com.example.todayweather.everyday.EverydayAdapter
import com.example.todayweather.everyday.EverydayViewModel
import com.example.todayweather.everyday.EverydayViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var everydayViewModel: EverydayViewModel
    private lateinit var everydayAdapter: EverydayAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingHome = FragmentHomeBinding.inflate(inflater)
        bindingHome.tvHomeStatusDescription.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sevenDaysFragment)
        }

        //
        val application = requireNotNull(this.activity).application
        val detailViewModelFactory = DetailViewModelFactory(application)
        val everyViewModelFactory = EverydayViewModelFactory(application)

        // Init ViewModel & Adapter
        detailViewModel =
            ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel::class.java)
        detailAdapter = DetailAdapter()
        everydayViewModel =
            ViewModelProvider(this, everyViewModelFactory).get(EverydayViewModel::class.java)
        everydayAdapter = EverydayAdapter()


        // Set data
        detailAdapter.dataList = detailViewModel.listData
        everydayAdapter.dataList = everydayViewModel.listData

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter
        bindingHome.recyclerViewEverydayContainerElement.recyclerViewEveryday.adapter = everydayAdapter

        return bindingHome.root
    }
}
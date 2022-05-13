package com.example.todayweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todayweather.databinding.FragmentHomeBinding
import com.example.todayweather.home.DetailAdapter
import com.example.todayweather.home.HomeViewModel
import com.example.todayweather.home.HomeViewModelFactory
import com.example.todayweather.detailgetapi.DetailGetApiViewModel
import com.example.todayweather.home.EverydayAdapter

class HomeFragment : Fragment() {
    private lateinit var bindingHome: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var everydayAdapter: EverydayAdapter

    private lateinit var detailGetApiViewModel: DetailGetApiViewModel

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
        val homeViewModelFactory = HomeViewModelFactory(application)

        // Init ViewModel & Adapter
        homeViewModel =
            ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
        detailAdapter = DetailAdapter()
        everydayAdapter = EverydayAdapter()

        detailGetApiViewModel = ViewModelProvider(this).get(DetailGetApiViewModel::class.java)

        // Set data
        detailAdapter.dataList = homeViewModel.listDataDetail
        everydayAdapter.dataList = homeViewModel.listDataEveryday

        // Set adapter
        bindingHome.recyclerViewDetailContainerElement.recyclerViewDetail.adapter = detailAdapter
        bindingHome.recyclerViewEverydayContainerElement.recyclerViewEveryday.adapter = everydayAdapter

        return bindingHome.root
    }
}
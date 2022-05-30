package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todayweather.adapter.SearchAdapter
import com.example.todayweather.databinding.FragmentSearchBinding
import com.example.todayweather.home.model.City
import com.example.todayweather.util.Utils
import com.example.todayweather.util.Utils.fromJsonToLocation

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    private var listCity = ArrayList<City>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        listCity = Utils.readJSONFromAsset(requireContext())?.fromJsonToLocation()!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAdapter = SearchAdapter()
        searchAdapter.listCities = listCity
        binding.rcvFragmentSearch.adapter = searchAdapter
    }
}
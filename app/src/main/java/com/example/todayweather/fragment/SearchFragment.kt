package com.example.todayweather.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todayweather.adapter.SearchAdapter
import com.example.todayweather.databinding.FragmentSearchBinding
import com.example.todayweather.home.model.City
import com.example.todayweather.util.JsonFileFactory

class SearchFragment : Fragment() {
    //    private lateinit var binding:
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter

    // Search fragment
    private lateinit var cityrcv: RecyclerView
    var listCity = listOf<City>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        listCity = JsonFileFactory().readFileJson("com/example/todayweather/assets/Cities.json")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        try {
////            cityrcv = requireActivity().findViewById(R.id.rcv_fragment_search)
//            binding.rcvFragmentSearch
//            cityrcv.layoutManager = LinearLayoutManager(cityrcv.context)
//            cityrcv.setHasFixedSize(true)
//            searchAdapter.listCities = JsonFileFactory().readFileJson("com/example/todayweather/assets/Cities.json")
//            cityrcv.adapter = searchAdapter
//            Toast.makeText(context, "Bug", Toast.LENGTH_SHORT).show()
//        } catch (ex: NullPointerException) {
//            ex.printStackTrace()
//            Log.i("fubug", ex.toString())
//        }

        searchAdapter = SearchAdapter()
        searchAdapter.listCities = listCity
        binding.rcvFragmentSearch.adapter = searchAdapter
    }
}
package com.example.todayweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.todayweather.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.txtStatusDescription.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_sevenDaysFragment)
        }

        return binding.root
    }
}
package com.example.ilt3_demo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ilt3_demo.api.RestoApiConfig
import com.example.ilt3_demo.data.RestaurantItem
import com.example.ilt3_demo.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private var recyclerAdapter: HomeRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAdapter = HomeRecyclerAdapter(::onRecyclerItemClick)
        binding?.recycler?.adapter = recyclerAdapter
        collectData()
    }


    override fun onDestroy() {
        super.onDestroy()
        binding?.recycler?.adapter = null
        binding = null
    }

    // additional function
    private fun onRecyclerItemClick(data: RestaurantItem){
        val actionToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
        findNavController().navigate(actionToDetail)
    }

    private fun collectData() {
        lifecycleScope.launchWhenResumed {
            launch(Dispatchers.IO) {
                val api = RestoApiConfig.getRestaurantApi()
                val restaurantData = api.getAllRestaurant()
                launch(Dispatchers.Main) {
                    displayData(restaurantData.restaurants)
                }
            }
        }
    }

    private fun displayData(restaurantData: List<RestaurantItem>){
        recyclerAdapter?.addAllData(restaurantData)
    }
}
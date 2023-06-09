package com.dicoding.vegefinder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.viewmodel.VegetableTypeViewModel
import com.dicoding.vegefinder.viewmodel.VegetableViewModel

class Explore : Fragment() {

    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var vegetableViewModel: VegetableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val exploreView = inflater.inflate(R.layout.fragment_explore, container, false)

        exploreAdapter = ExploreAdapter(requireContext())
        exploreAdapter.notifyDataSetChanged()

        val recyclerViewExplore = exploreView.findViewById<RecyclerView>(R.id.rv_explore)
        recyclerViewExplore.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerViewExplore.adapter = exploreAdapter

        vegetableViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[VegetableViewModel::class.java]

        vegetableViewModel.setVegetable()
        vegetableViewModel.getVegetableResponse().observe(viewLifecycleOwner){vegetableList ->
            exploreAdapter.setVegetableList(vegetableList)
        }


        return exploreView
    }
}
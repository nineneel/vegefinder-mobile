package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.viewmodel.VegetableViewModel

class Explore : Fragment() {

    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var vegetableViewModel: VegetableViewModel
    private lateinit var sessionManager: SessionManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val exploreView = inflater.inflate(R.layout.fragment_explore, container, false)

        sessionManager = SessionManager(requireContext())

        exploreAdapter = ExploreAdapter(requireContext())
        exploreAdapter.notifyDataSetChanged()

        val recyclerViewExplore = exploreView.findViewById<RecyclerView>(R.id.rv_explore)
        recyclerViewExplore.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerViewExplore.adapter = exploreAdapter

        val vegetableList = sessionManager.getVegetableList()

        if (vegetableList != null) {
            if (vegetableList.size > 0) {
                exploreAdapter.setVegetableList(vegetableList)
                Log.d("EXPLORE TEXT", "Vegetable List: $vegetableList")
            } else {
                vegetableViewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.NewInstanceFactory()
                )[VegetableViewModel::class.java]

                vegetableViewModel.setVegetable()
                vegetableViewModel.getVegetableResponse().observe(viewLifecycleOwner) { response ->
                    if (response != null) {
                        exploreAdapter.setVegetableList(response)
                        sessionManager.saveVegetableList(response)
                    }else{
                        Toast.makeText(requireContext(), "Its a toast!", Toast.LENGTH_SHORT).show()

                    }

                }
            }
        }



        return exploreView
    }
}
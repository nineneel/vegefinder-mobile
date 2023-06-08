package com.dicoding.vegefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.Adapter.ExploreData

class Explore : Fragment() {

    private val exploreList = listOf(
        ExploreData(
            R.drawable.kangkung,
            "Bayam hijau putih",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        ),
        ExploreData(
            R.drawable.kangkung,
            "Bayam",
            "nananana"
        )
    )


    private lateinit var exploreAdapter: ExploreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val exploreView = inflater.inflate(R.layout.fragment_explore, container, false)



        val recyclerViewExplore = exploreView.findViewById<RecyclerView>(R.id.rv_explore)
        recyclerViewExplore.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerViewExplore.adapter = ExploreAdapter(exploreList, requireContext())

        return exploreView
    }
}
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
import com.dicoding.vegefinder.Adapter.History
import com.dicoding.vegefinder.Adapter.JenisAdapter

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        val recyclerViewExplore = view.findViewById<RecyclerView>(R.id.rv_explore)
        recyclerViewExplore.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerViewExplore.adapter = ExploreAdapter(exploreList, requireContext())

        return view
    }
}
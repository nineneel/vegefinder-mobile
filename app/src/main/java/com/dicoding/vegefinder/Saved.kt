package com.dicoding.vegefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.Adapter.SavedAdapter
import com.dicoding.vegefinder.Adapter.SavedData

class Saved : Fragment() {
    private val savedList = listOf(
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        SavedData(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val recyclerViewSaved = view.findViewById<RecyclerView>(R.id.rv_saved)
        recyclerViewSaved.layoutManager = LinearLayoutManager(activity)
        recyclerViewSaved.adapter = SavedAdapter(savedList, requireContext())

        return view
    }
}
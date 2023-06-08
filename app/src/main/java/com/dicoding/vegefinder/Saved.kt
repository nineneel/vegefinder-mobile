package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreAdapter
import com.dicoding.vegefinder.Adapter.SavedAdapter
import com.dicoding.vegefinder.Adapter.SavedData
import com.dicoding.vegefinder.viewmodel.SavedViewModel
import com.dicoding.vegefinder.viewmodel.VegetableViewModel

class Saved : Fragment() {
    private lateinit var recyclerViewSaved: RecyclerView
    private lateinit var savedAdapter: SavedAdapter
    private lateinit var savedViewModel: SavedViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        savedAdapter = SavedAdapter(requireContext())
        savedAdapter.notifyDataSetChanged()

        recyclerViewSaved = view.findViewById(R.id.rv_saved)
        recyclerViewSaved.layoutManager = LinearLayoutManager(activity)
        recyclerViewSaved.adapter = savedAdapter

        savedViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[SavedViewModel::class.java]

        savedViewModel.setVegetable()
        savedViewModel.getVegetableResponse().observe(viewLifecycleOwner){vegetableList ->
            savedAdapter.setVegetableList(vegetableList)
        }

        return view
    }
}
package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.HistoryActivity
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.VegetableTypeAdapter
import com.dicoding.vegefinder.viewmodel.HomeHistoryViewModel
import com.dicoding.vegefinder.viewmodel.HomeTypeViewModel

class Home : Fragment() {

    private lateinit var historyViewAllTextView: TextView
    private lateinit var homeTypeViewModel: HomeTypeViewModel
    private lateinit var homeHistoryViewModel: HomeHistoryViewModel
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var vegetableTypeAdapter: VegetableTypeAdapter
    private lateinit var sessionManager: SessionManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val b1: Button = view.findViewById(R.id.btn_scan)
        b1.setOnClickListener {
            val i = Intent(activity, CameraActivity::class.java)
            startActivity(i)
        }

        sessionManager = SessionManager(requireContext())

        vegetableTypeAdapter = VegetableTypeAdapter(requireContext())
        vegetableTypeAdapter.notifyDataSetChanged()

        historyAdapter = HistoryAdapter(requireContext())
        historyAdapter.notifyDataSetChanged()

        val recyclerViewHistories = view.findViewById<RecyclerView>(R.id.rv_history)
        recyclerViewHistories.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewHistories.adapter = historyAdapter


        val recyclerViewType = view.findViewById<RecyclerView>(R.id.rv_jenis)
        recyclerViewType.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewType.adapter = vegetableTypeAdapter

        homeTypeViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeTypeViewModel::class.java]
        homeHistoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeHistoryViewModel::class.java]


        val vegetableTypeList = sessionManager.getVegetableTypeList()

        if (vegetableTypeList.size > 0) {
            Log.d("EXPLORE TEXT", "Vegetable List: $vegetableTypeList")
            vegetableTypeAdapter.setList(vegetableTypeList)
        } else {

            homeTypeViewModel.setVegetableType()
            homeTypeViewModel.getVegetableTypeResponse().observe(viewLifecycleOwner) { response ->
                vegetableTypeAdapter.setList(response)
                sessionManager.saveVegetableTypeList(response)
            }
        }


        homeHistoryViewModel.setHistory()
        homeHistoryViewModel.getHistoryResponse().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                historyAdapter.setVegetableList(response)
            } else {
                Log.d("HOME", "Home histories null")
            }
        }

        historyViewAllTextView = view.findViewById(R.id.view_all)
        historyViewAllTextView.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        historyAdapter.notifyDataSetChanged()

        homeHistoryViewModel.setHistory()
        homeHistoryViewModel.getHistoryResponse().observe(viewLifecycleOwner) { response ->
            if (response != null) {
                historyAdapter.setVegetableList(response)
            } else {
                Log.d("HOME", "Home histories null")
            }
        }

    }
}
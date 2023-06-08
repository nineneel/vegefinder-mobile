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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Activity.HistoryActivity
import com.dicoding.vegefinder.Adapter.History
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.VegetableTypeAdapter
import com.dicoding.vegefinder.viewmodel.VegetableTypeViewModel

class Home : Fragment() {

    private val historyList = listOf(
        History(
            R.drawable.kangkung,
            "Bayam",
            "Daun",
            "Nnanananan"
        ),
        History(
            R.drawable.bayam,
            "Bayam Hijau",
            "Daun",
            "Nnanananan"
        )

    )

    private lateinit var viewTextView: TextView
    private lateinit var vegetableTypeViewModel: VegetableTypeViewModel
    private lateinit var vegetableTypeAdapter: VegetableTypeAdapter

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


        val recyclerViewHistory = view.findViewById<RecyclerView>(R.id.rv_history)
        recyclerViewHistory.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerViewHistory.adapter = HistoryAdapter(historyList, requireContext())


        vegetableTypeAdapter = VegetableTypeAdapter(requireContext())
        vegetableTypeAdapter.notifyDataSetChanged()

        val recyclerViewJenis = view.findViewById<RecyclerView>(R.id.rv_jenis)
        recyclerViewJenis.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerViewJenis.adapter = vegetableTypeAdapter

        vegetableTypeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[VegetableTypeViewModel::class.java]

        vegetableTypeViewModel.setVegetableType()
        vegetableTypeViewModel.getVegetableTypeResponse().observe(viewLifecycleOwner){ listJenis ->
            Log.d("HOME","GetJenisResponse ${listJenis[0].name}")
            vegetableTypeAdapter.setList(listJenis)
        }

        Log.d("HOME","GET USER TOKEN ${SessionManager.userToken}")


        viewTextView = view.findViewById(R.id.view_all)
        viewTextView.setOnClickListener {
            val intent = Intent(requireContext(), HistoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
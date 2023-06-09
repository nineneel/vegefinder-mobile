package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.History
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.HistoryallAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.viewmodel.HistoryViewModel
import com.dicoding.vegefinder.viewmodel.HomeHistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyAdapter = HistoryAdapter(this)
        historyAdapter.notifyDataSetChanged()

        val historyRecyclerView = findViewById<RecyclerView>(R.id.hty_all)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter

        historyViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HistoryViewModel::class.java]

        historyViewModel.setHistory()
        historyViewModel.getHistoryResponse().observe(this){response->
            historyAdapter.setVegetableList(response)
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
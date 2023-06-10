package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.HistoryAllAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.databinding.ActivityHistoryBinding
import com.dicoding.vegefinder.databinding.FragmentSavedBinding
import com.dicoding.vegefinder.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAllAdapter: HistoryAllAdapter
    private lateinit var historyViewModel: HistoryViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        val historyRecyclerView = findViewById<RecyclerView>(R.id.hty_all)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAllAdapter

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        historyViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HistoryViewModel::class.java]

        historyViewModel.setHistory()
        historyViewModel.getHistoryResponse().observe(this){response->
            historyAllAdapter.setVegetableList(response)
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
package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.HistoryAllAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.viewmodel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyAllAdapter: HistoryAllAdapter
    private lateinit var historyViewModel: HistoryViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val tvHistory = findViewById<TextView>(R.id.tv_nohistory)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        historyAllAdapter = HistoryAllAdapter(this)
        historyAllAdapter.notifyDataSetChanged()

        val historyRecyclerView = findViewById<RecyclerView>(R.id.hty_all)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAllAdapter


        historyViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HistoryViewModel::class.java]

        historyViewModel.setHistory()
        historyViewModel.getHistoryResponse().observe(this){response->
            if(response.size > 0){
                historyAllAdapter.setVegetableList(response)
                tvHistory.visibility = View.GONE
            }
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
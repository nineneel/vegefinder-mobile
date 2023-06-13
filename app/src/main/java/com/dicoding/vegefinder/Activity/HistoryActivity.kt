package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
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
        val pbHistory = findViewById<ProgressBar>(R.id.pb_history)

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


        showLoading(pbHistory, true)
        historyViewModel.setHistory()
        historyViewModel.getHistoryResponse().observe(this){response->
            showLoading(pbHistory, false)
            if(response.size > 0){
                historyAllAdapter.setVegetableList(response)
            }else{
                tvHistory.visibility = View.VISIBLE
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

    private fun showLoading(progressBar: ProgressBar, state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
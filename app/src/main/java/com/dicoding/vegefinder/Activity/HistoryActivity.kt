package com.dicoding.vegefinder.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.ExploreData
import com.dicoding.vegefinder.Adapter.History
import com.dicoding.vegefinder.Adapter.HistoryAdapter
import com.dicoding.vegefinder.Adapter.HistoryallAdapter
import com.dicoding.vegefinder.R

class HistoryActivity : AppCompatActivity() {

    private val historyList = listOf(
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        ),
        History(
            R.drawable.kangkung,
            "Bayam",
            "nananana",
            "yayayayay"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "History"

        val recyclerViewHistory = findViewById<RecyclerView>(R.id.hty_all)
        recyclerViewHistory.layoutManager = LinearLayoutManager(this)
        recyclerViewHistory.adapter = HistoryAdapter(historyList, this)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.HistoryAllAdapter
import com.dicoding.vegefinder.Adapter.TypeAllAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.VegetableType
import com.dicoding.vegefinder.viewmodel.HistoryViewModel
import com.dicoding.vegefinder.viewmodel.HomeTypeViewModel

class TypesActivity : AppCompatActivity() {

    private lateinit var typeAllAdapter: TypeAllAdapter
    private lateinit var homeTypeViewModel: HomeTypeViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jenis)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val tvNoView = findViewById<TextView>(R.id.tv_no_view)
        val pbJenis = findViewById<ProgressBar>(R.id.pb_jenis)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        typeAllAdapter = TypeAllAdapter(this)
        typeAllAdapter.notifyDataSetChanged()

        val historyRecyclerView = findViewById<RecyclerView>(R.id.type_all)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = typeAllAdapter

        homeTypeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeTypeViewModel::class.java]

        showLoading(pbJenis, true)
        homeTypeViewModel.setVegetableType()
        homeTypeViewModel.getVegetableTypeResponse().observe(this) { response ->
            if (response != null){
                showLoading(pbJenis, false)
                typeAllAdapter.setVegetableTypeList(response)
            }

            tvNoView.visibility = View.GONE
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
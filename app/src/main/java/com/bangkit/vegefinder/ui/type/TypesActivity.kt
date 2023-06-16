package com.bangkit.vegefinder.ui.type

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
import com.bangkit.vegefinder.adapter.TypeAllAdapter
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.viewmodel.TypeViewModel

class TypesActivity : AppCompatActivity() {

    private lateinit var typeAllAdapter: TypeAllAdapter
    private lateinit var typeViewModel: TypeViewModel


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type)

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

        typeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TypeViewModel::class.java]

        showLoading(pbJenis, true)
        typeViewModel.setVegetableType()
        typeViewModel.getVegetableTypeResponse().observe(this) { response ->
            showLoading(pbJenis, false)
            if (response != null){
                typeAllAdapter.setVegetableTypeList(response)
                tvNoView.visibility = View.GONE
            }else{
                ToastDisplay.displayToastWithMessage(this, "Can't fetch vegetable type!")
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
package com.bangkit.vegefinder.ui.store.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bangkit.vegefinder.R
import com.bumptech.glide.Glide

class DetailProductActivity : AppCompatActivity() {
    private var totalCart = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val price = intent.getStringExtra("price")
        val description = intent.getStringExtra("description")

        val thumbnailImageView = findViewById<ImageView>(R.id.iv_product)
        val nameTextView = findViewById<TextView>(R.id.tv_name)
        val priceTextView = findViewById<TextView>(R.id.tv_price)
        val descriptionTextView = findViewById<TextView>(R.id.tv_description_store)

        Glide.with(this)
            .asBitmap()
            .load(image)
            .centerCrop()
            .placeholder(R.drawable.vegefinder)
            .into(thumbnailImageView)

        nameTextView.text = name
        priceTextView.text = price
        descriptionTextView.text = description

        val totalCartTextView = findViewById<TextView>(R.id.tv_total_cart)
        val minButton = findViewById<Button>(R.id.btn_min_cart)
        val plusButton = findViewById<Button>(R.id.btn_plus_cart)

        minButton.setOnClickListener {
            if (totalCart > 1){
                totalCart --
                totalCartTextView.text = totalCart.toString()
            }
        }

        plusButton.setOnClickListener {
            totalCart ++
            totalCartTextView.text = totalCart.toString()
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
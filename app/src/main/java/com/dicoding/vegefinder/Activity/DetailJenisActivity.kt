package com.dicoding.vegefinder.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.R


class DetailJenisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailjenis)



        val typeVege = intent.getStringExtra("typeVege")
        val type = intent.getStringExtra("type")
        val desc = intent.getStringExtra("desc")
        val image = intent.getStringExtra("image")

        val typeVegeTextView = findViewById<TextView>(R.id.detail_typeVege)
        val typeTextView = findViewById<TextView>(R.id.detail_type)
        val descTextView = findViewById<TextView>(R.id.detail_desc)
        val imageView = findViewById<ImageView>(R.id.detail_image)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        typeVegeTextView.text = typeVege
        typeTextView.text = type
        descTextView.text = desc
        Glide.with(imageView)
            .asBitmap()
            .load("https://storage.googleapis.com/vegefinder-bucket/${image}")
            .centerCrop()
            .into(imageView)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
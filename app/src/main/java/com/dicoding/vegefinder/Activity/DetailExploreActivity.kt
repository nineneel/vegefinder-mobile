package com.dicoding.vegefinder.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Adapter.TypeTagAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable
import org.w3c.dom.Text

class DetailExploreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailexplore)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val name = intent.getStringExtra("name")
        val types = intent.getStringArrayListExtra("types")
        val description = intent.getStringExtra("description")
        val descriptionSource = intent.getStringExtra("descriptionSource")
        val thumbnail = intent.getStringExtra("thumbnail")
        val howToPlant = intent.getStringExtra("howToPlant")
        val howToPlantSource = intent.getStringExtra("howToPlantSource")
        val plantCare = intent.getStringExtra("plantCare")
        val plantCareSource = intent.getStringExtra("plantCareSource")

        val nameTextView = findViewById<TextView>(R.id.tv_name)
        val recyclerViewType = findViewById<RecyclerView>(R.id.rv_types)
        val thumbnailImageView = findViewById<ImageView>(R.id.iv_thumbnail)
        val descriptionTextView = findViewById<TextView>(R.id.tv_description)
        val descriptionSourceTextView = findViewById<TextView>(R.id.tv_description_source)
        val howToPlantTextView = findViewById<TextView>(R.id.tv_how_to_plant)
        val howToPlantTextViewSource = findViewById<TextView>(R.id.tv_how_to_plant_source)
        val plantCareTextView = findViewById<TextView>(R.id.tv_plant_care)
        val plantCareTextViewSource = findViewById<TextView>(R.id.tv_plant_care_source)

//        Log.d("DEBUG", "TEST TYPE $types")

        recyclerViewType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewType.adapter = types?.let { TypeTagAdapter(it, this) }

        Glide.with(thumbnailImageView)
            .asBitmap()
            .load("https://storage.googleapis.com/vegefinder-bucket/${thumbnail}")
            .centerCrop()
            .placeholder(R.drawable.kangkung)
            .into(thumbnailImageView)
        nameTextView.text = name
        descriptionTextView.text = description
        descriptionSourceTextView.text = descriptionSource
        howToPlantTextView.text = howToPlant
        howToPlantTextViewSource.setOnClickListener{
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(howToPlantSource))
            startActivity(websiteIntent)
        }
        plantCareTextView.text = plantCare
        plantCareTextViewSource.setOnClickListener{
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(plantCareSource))
            startActivity(websiteIntent)
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
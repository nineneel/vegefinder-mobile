package com.dicoding.vegefinder.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Adapter.ImageSlideAdapter
import com.dicoding.vegefinder.Adapter.TypeTagAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.databinding.ActivityDetailexploreBinding
import com.dicoding.vegefinder.getScreenHeight
import com.dicoding.vegefinder.viewmodel.SaveVegetableViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class DetailExploreActivity : AppCompatActivity() {

    private lateinit var saveVegetableViewModel: SaveVegetableViewModel
    private lateinit var binding: ActivityDetailexploreBinding
    private lateinit var adapter: ImageSlideAdapter
    private val list = ArrayList<Vegetable>()
    private lateinit var dots: ArrayList<TextView>
    private var isSavedVegetable: Boolean = false
    private var isProccess: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailexploreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.add(
            Vegetable(
                "https://storage.googleapis.com/vegefinder-bucket/"
            )
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val bottom_sheet = findViewById<FrameLayout>(R.id.bottom_sheet)

        BottomSheetBehavior.from(bottom_sheet).apply {
            peekHeight = getScreenHeight() - 620
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        saveVegetableViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SaveVegetableViewModel::class.java]

        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")
        val typesName = intent.getStringArrayListExtra("typesName")
        val typesGroupName = intent.getIntegerArrayListExtra("typesGroupsName")
        val images = intent.getStringArrayListExtra("images")
        val description = intent.getStringExtra("description")
        val descriptionSource = intent.getStringExtra("descriptionSource")
        val thumbnail = intent.getStringExtra("thumbnail")
        val howToPlant = intent.getStringExtra("howToPlant")
        val howToPlantSource = intent.getStringExtra("howToPlantSource")
        val plantCare = intent.getStringExtra("plantCare")
        val plantCareSource = intent.getStringExtra("plantCareSource")
        val plantDisease = intent.getStringExtra("plantDisease")
        val plantDiseaseSource = intent.getStringExtra("plantDiseaseSource")
        val isSaved = intent.getBooleanExtra("isSaved", false)


        val nameTextView = findViewById<TextView>(R.id.tv_name)
        val recyclerViewType = findViewById<RecyclerView>(R.id.rv_types)
        val thumbnailImageView = findViewById<ImageView>(R.id.iv_thumbnail)
        val descriptionTextView = findViewById<TextView>(R.id.tv_description)
        val descriptionSourceTextView = findViewById<TextView>(R.id.tv_description_source)
        val howToPlantTextView = findViewById<TextView>(R.id.tv_how_to_plant)
        val howToPlantTextViewSource = findViewById<TextView>(R.id.tv_how_to_plant_source)
        val plantCareTextView = findViewById<TextView>(R.id.tv_plant_care)
        val plantCareTextViewSource = findViewById<TextView>(R.id.tv_plant_care_source)
        val plantDiseaseTextView = findViewById<TextView>(R.id.tv_plant_disease)
        val plantDiseaseTextViewSource = findViewById<TextView>(R.id.tv_plant_disease_source)
        val saveButton = findViewById<Button>(R.id.btn_saved)

        isSavedVegetable = isSaved
        activeToogleSaveButton(saveButton)

        recyclerViewType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewType.adapter = typesName?.let {
            typesGroupName?.let { it1 ->
                TypeTagAdapter(
                    it,
                    it1,
                    true
                )
            }
        }

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
        howToPlantTextViewSource.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(howToPlantSource))
            startActivity(websiteIntent)
        }
        plantCareTextView.text = plantCare
        plantCareTextViewSource.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(plantCareSource))
            startActivity(websiteIntent)
        }
        plantDiseaseTextView.text = plantDisease
        plantDiseaseTextViewSource.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(plantDiseaseSource))
            startActivity(websiteIntent)
        }

        saveButton.setOnClickListener {
            if (!isProccess) {
                isProccess = true
                saveVegetableViewModel.setSaveVegetable(id = id)
                saveVegetableViewModel.getSaveVegetableResponse().observe(this) { response ->
                    if (response != null) {
                        if (isSavedVegetable) {
                            isSavedVegetable = false
                            activeToogleSaveButton(saveButton)
                        } else {
                            isSavedVegetable = true
                            activeToogleSaveButton(saveButton)
                        }
                        isProccess = false
                    } else {
                        Log.d("TEST EXPLORE", "Test save vegetable null")
                    }
                }
            }

        }
    }

    private fun activeToogleSaveButton(saveButton: Button) {
        if (isSavedVegetable) {
            saveButton.text = "Unsave This Vegetable"
            saveButton.background =
                ContextCompat.getDrawable(this, R.drawable.button_saved_deactive)
        } else {
            saveButton.text = "Save This Vegetable"
            saveButton.background = ContextCompat.getDrawable(this, R.drawable.button_scan)
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
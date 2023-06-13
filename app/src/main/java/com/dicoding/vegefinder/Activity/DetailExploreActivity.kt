package com.dicoding.vegefinder.Activity

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.Adapter.ImageSlideAdapter
import com.dicoding.vegefinder.Adapter.TypeTagAdapter
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.getScreenHeight
import com.dicoding.vegefinder.viewmodel.SaveVegetableViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailExploreActivity : AppCompatActivity() {

    private lateinit var saveVegetableViewModel: SaveVegetableViewModel

    private var isSavedVegetable: Boolean = false
    private var isProccess: Boolean = false

    private lateinit var imageSlideAdapter: ImageSlideAdapter
    private lateinit var imageDotsLinearLayout: LinearLayout
    private val imageList = ArrayList<String>()
    private lateinit var dots: ArrayList<TextView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailexplore)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val bottom_sheet = findViewById<FrameLayout>(R.id.bottom_sheet)

        BottomSheetBehavior.from(bottom_sheet).apply {
            peekHeight = getScreenHeight() - 780
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        saveVegetableViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[SaveVegetableViewModel::class.java]

        val probabilities: String? = intent.getStringExtra("probabilities")
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
        val imageViewPager = findViewById<ViewPager2>(R.id.view_pager)
        imageDotsLinearLayout = findViewById(R.id.dots_indicator)
        val fromPredictionImageView = findViewById<ImageView>(R.id.from_prediction)
        val fromPredictionTextView = findViewById<TextView>(R.id.tv_from_prediction)
        val descriptionTextView = findViewById<TextView>(R.id.tv_description)
        val descriptionSourceTextView = findViewById<TextView>(R.id.tv_description_source)
        val howToPlantTextView = findViewById<TextView>(R.id.tv_how_to_plant)
        val howToPlantTextViewSource = findViewById<TextView>(R.id.tv_how_to_plant_source)
        val plantCareTextView = findViewById<TextView>(R.id.tv_plant_care)
        val plantCareTextViewSource = findViewById<TextView>(R.id.tv_plant_care_source)
        val plantDiseaseTextView = findViewById<TextView>(R.id.tv_plant_disease)
        val plantDiseaseTextViewSource = findViewById<TextView>(R.id.tv_plant_disease_source)
        val saveButton = findViewById<FloatingActionButton>(R.id.btn_saved)

        images?.map{imageList.add("https://storage.googleapis.com/vegefinder-bucket/$it")}

        if(probabilities != null){
            fromPredictionImageView.visibility = View.VISIBLE
            fromPredictionTextView.visibility = View.VISIBLE
            fromPredictionTextView.text = "Success, with ${(probabilities.substring(0, 5))}% probabilities"
        }

        imageSlideAdapter = ImageSlideAdapter(imageList)
        imageViewPager.adapter = imageSlideAdapter

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

        nameTextView.text = name
        descriptionTextView.text = description
        descriptionSourceTextView.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse(descriptionSource))
            startActivity(websiteIntent)
        }
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

        dots = ArrayList()
        setIndicator()

        imageViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })
    }

    private fun selectedDot(position: Int){
        for(i in 0 until imageList.size){
            if(i == position)
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.orange))
            else
                dots[i].setTextColor(ContextCompat.getColor(this, R.color.grey))

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun activeToogleSaveButton(saveButton: FloatingActionButton) {
        if (isSavedVegetable) {
            saveButton.backgroundTintList = ColorStateList.valueOf(Color.rgb(231, 231, 231))
        } else {
            saveButton.backgroundTintList = ColorStateList.valueOf(Color.rgb(89, 147, 68))
        }
    }

    private fun setIndicator(){
        for(i in 0 until imageList.size){
            dots.add(TextView(this))
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            }else{
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 18f
            imageDotsLinearLayout.addView(dots[i])
        }
    }

}
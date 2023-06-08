package com.dicoding.vegefinder

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.dicoding.vegefinder.Activity.DetailExploreActivity
import com.dicoding.vegefinder.data.request.PredictRequest
import com.dicoding.vegefinder.databinding.ActivityCameraBinding
import com.dicoding.vegefinder.viewmodel.PredictViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private lateinit var imageResult: ImageView
    private lateinit var uploadButton: Button
    private lateinit var takePictureBtn: FloatingActionButton
    private lateinit var predictViewModel: PredictViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var container: ConstraintLayout

    var currentPhotoPath = ""
    private var selectedImageFile: File? = null

    companion object {
        private const val MAX_FONT_SIZE = 50F
        private const val TAG = "ObjectDetectionActivity"
        val REQUIRED_PERMISSIONS_CAMERA = arrayOf(android.Manifest.permission.CAMERA)
        const val REQUEST_CODE_CAMERA = 10
        const val AUTHOR = "com.example.tfliteexample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_camera)

        imageResult = findViewById(R.id.imageResult)
        progressBar = findViewById(R.id.progress_bar)
        container = findViewById(R.id.container)

        predictViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[PredictViewModel::class.java]

        takePictureBtn = findViewById(R.id.takePictureBtn)
        takePictureBtn.setOnClickListener {
            startIntentCamera()
        }

        uploadButton = findViewById(R.id.btn_upload)
        uploadButton.setOnClickListener{
            checkInputs()
        }
    }

    private fun startIntentCamera() {
        if(allCameraPermissionsGranted()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)
            createCustomTempFile(application).also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this@CameraActivity,
                    "com.dicoding.vegefinder",
                    it
                )
                currentPhotoPath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                launcherIntentCamera.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS_CAMERA,
                REQUEST_CODE_CAMERA
            )
        }
    }

    private fun allCameraPermissionsGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun uploadListener(imageMultipart: MultipartBody.Part){
        showLoading(true)
        predictViewModel.sendVegetableImage(imageMultipart)
        predictViewModel.getPredictResponse().observe(this){response ->
            showLoading(false)
            if(response != null) {
                Log.d("CAMERA CHECK", "Works: $response")
                val intent = Intent(this, DetailExploreActivity::class.java)
                intent.putExtra("name", response.vegetable?.name)
                intent.putExtra("types", response.vegetable?.types?.map { it.name } as ArrayList<String>)
                intent.putExtra("description", response.vegetable.description)
                intent.putExtra("descriptionSource", response.vegetable.descriptionSource)
                intent.putExtra("thumbnail", response.vegetable.thumbnail)
                intent.putExtra("howToPlant", response.vegetable.howToPlant)
                intent.putExtra("howToPlantSource", response.vegetable.howToPlantSource)
                intent.putExtra("plantCare", response.vegetable.plantCare)
                intent.putExtra("plantCareSource", response.vegetable.plantCareSource)

                this.startActivity(intent)
            }else{
                Log.d("CAMERA CHECK", "Works: null")
                Toast.makeText(this@CameraActivity, "Response IS NULL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInputs(){
        if(selectedImageFile == null) {
            Toast.makeText(this@CameraActivity, "Select or Photo Image", Toast.LENGTH_SHORT).show()
        } else {
            selectedImageFile?.let {
                val imageMultipart = MultipartBody.Part.createFormData(
                    "image",
                    it.name,
                    RequestBody.create(MediaType.parse("image/*"), it)
                )
                uploadListener(imageMultipart)
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            selectedImageFile = myFile
            myFile.let { file ->
                imageResult.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }


    private fun showLoading(state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
        container.visibility = if (state) View.GONE else View.VISIBLE
    }
}

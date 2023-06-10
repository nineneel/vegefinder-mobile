package com.dicoding.vegefinder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
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
import com.dicoding.vegefinder.helper.FileHelper
import com.dicoding.vegefinder.viewmodel.PredictViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private lateinit var imageResult: ImageView
    private lateinit var uploadButton: Button
    private lateinit var takePictureBtn: ImageButton
    private lateinit var predictViewModel: PredictViewModel
    private lateinit var loadingLayout: View
    private lateinit var container: ConstraintLayout
    private lateinit var currentPhotoPath : String
    private lateinit var sessionManager: SessionManager
    private lateinit var deletePictureBtn: ImageButton

    private var isProcessImage = false
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
        loadingLayout = findViewById(R.id.loading_layout)
        container = findViewById(R.id.container)

        sessionManager = SessionManager(this)

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

        deletePictureBtn = findViewById(R.id.btn_delete_picture)
        deletePictureBtn.setOnClickListener{
            removeImageResult()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startIntentCamera() {
        if(!isProcessImage){
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
        }else{
            Toast.makeText(this@CameraActivity, "Please be patient", Toast.LENGTH_SHORT).show()
        }

    }

    private fun allCameraPermissionsGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun uploadListener(imageMultipart: MultipartBody.Part){
        val user = sessionManager.getUser()
        val userId = user?.id ?: 0
        showLoading(true)
        predictViewModel.sendVegetableImage(imageMultipart, userId)
        predictViewModel.getPredictResponse().observe(this){response ->
            if(response != null && response.status == "success") {
                Log.d("CAMERA CHECK", "Works: $response")
                val intent = Intent(this, DetailExploreActivity::class.java)
                intent.putExtra("name", response.vegetable?.name)
                intent.putExtra("typesName", response.vegetable?.types?.map { it.name } as ArrayList<String>)
                intent.putExtra("typesGroupsName", response.vegetable.types.map { it.typeGroups.id } as ArrayList<Int>)
                intent.putExtra("description", response.vegetable.description)
                intent.putExtra("descriptionSource", response.vegetable.descriptionSource)
                intent.putExtra("images", response.vegetable.images)
                intent.putExtra("thumbnail", response.vegetable.thumbnail)
                intent.putExtra("howToPlant", response.vegetable.howToPlant)
                intent.putExtra("howToPlantSource", response.vegetable.howToPlantSource)
                intent.putExtra("plantCare", response.vegetable.plantCare)
                intent.putExtra("plantCareSource", response.vegetable.plantCareSource)
                intent.putExtra("plantDisease", response.vegetable.plantDisease)
                intent.putExtra("plantDiseaseSource", response.vegetable.plantDiseaseSource)

                finish()
                this.startActivity(intent)
            }else{
                isProcessImage = false
                Log.d("CAMERA CHECK", "Works: $response")
                Toast.makeText(this@CameraActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
            showLoading(false)
        }
    }

    private fun checkInputs(){
        if(selectedImageFile == null) {
            Toast.makeText(this@CameraActivity, "Select Image or Take a Photo", Toast.LENGTH_SHORT).show()
        } else {
            if(!isProcessImage){
                isProcessImage = true
                selectedImageFile?.let {
                    val imageMultipart = MultipartBody.Part.createFormData(
                        "image",
                        it.name,
                        RequestBody.create(MediaType.parse("image/*"), it)
                    )
                    uploadListener(imageMultipart)
                }
            }else{
                Toast.makeText(this@CameraActivity, "Please be patient", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            selectedImageFile = FileHelper.reduceFileImage(File(currentPhotoPath))
            if(selectedImageFile != null) {
                val result =  BitmapFactory.decodeFile(selectedImageFile!!.path)
                imageResult.setImageBitmap(result)
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeImageResult(){
        imageResult.setImageDrawable(null)
        selectedImageFile = null
    }

    private fun showLoading(state: Boolean) {
        loadingLayout.visibility = if (state) View.VISIBLE else View.GONE
    }
}

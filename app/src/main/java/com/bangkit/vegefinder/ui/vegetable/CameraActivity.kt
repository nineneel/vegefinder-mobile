package com.bangkit.vegefinder.ui.vegetable

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.Result
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.databinding.ActivityCameraBinding
import com.bangkit.vegefinder.helper.FileHelper
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.helper.createCustomTempFile
import com.bangkit.vegefinder.helper.rotateFile
import com.bangkit.vegefinder.viewmodel.PredictViewModel
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
    private lateinit var currentPhotoPath: String
    private lateinit var sessionManager: SessionManager
    private lateinit var deletePictureBtn: ImageButton

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

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        imageResult = findViewById(R.id.imageResult)
        loadingLayout = findViewById(R.id.loading_layout)
        container = findViewById(R.id.container)

        sessionManager = SessionManager(this)

        predictViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[PredictViewModel::class.java]

        takePictureBtn = findViewById(R.id.takePictureBtn)
        takePictureBtn.setOnClickListener {
            if (!checkIsProcessImage()) {
                startIntentCamera()
            } else {
                Toast.makeText(this@CameraActivity, "Image already exist!", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        uploadButton = findViewById(R.id.btn_upload)
        uploadButton.setOnClickListener {
            if (checkIsProcessImage()) {
                checkInputs()
            } else {
                Toast.makeText(
                    this@CameraActivity, "Select Image or Take a Photo!", Toast.LENGTH_SHORT
                ).show()

            }
        }

        deletePictureBtn = findViewById(R.id.btn_delete_picture)
        deletePictureBtn.setOnClickListener {
            removeImageResult()
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startIntentCamera() {
        if (allCameraPermissionsGranted()) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.resolveActivity(packageManager)
            createCustomTempFile(application).also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this@CameraActivity, "com.bangkit.vegefinder", it
                )
                currentPhotoPath = it.absolutePath
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                launcherIntentCamera.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS_CAMERA, REQUEST_CODE_CAMERA
            )
        }

    }

    private fun allCameraPermissionsGranted() = REQUIRED_PERMISSIONS_CAMERA.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun uploadListener(imageMultipart: MultipartBody.Part) {
        val user = sessionManager.getUser()
        val userId = user?.id ?: 0
        try {
            predictViewModel.predict(imageMultipart, userId).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Success -> {
                        showLoading(false)
                        val response = result.data
                        if (response != null && response.status == "success") {
                            val intent = Intent(this, VegetableActivity::class.java)
                            intent.putExtra("probabilities", response.probabilities)
                            intent.putExtra("name", response.vegetable?.name)
                            intent.putExtra("typesName",
                                response.vegetable?.types?.map { it.name } as ArrayList<String>)
                            intent.putExtra("typesGroupsName",
                                response.vegetable.types.map { it.typeGroups.id } as ArrayList<Int>)
                            intent.putExtra("description", response.vegetable.description)
                            intent.putExtra(
                                "descriptionSource", response.vegetable.descriptionSource
                            )
                            intent.putExtra("images", response.vegetable.images)
                            intent.putExtra("thumbnail", response.vegetable.thumbnail)
                            intent.putExtra("howToPlant", response.vegetable.howToPlant)
                            intent.putExtra("howToPlantSource", response.vegetable.howToPlantSource)
                            intent.putExtra("plantCare", response.vegetable.plantCare)
                            intent.putExtra("plantCareSource", response.vegetable.plantCareSource)
                            intent.putExtra("plantDisease", response.vegetable.plantDisease)
                            intent.putExtra(
                                "plantDiseaseSource", response.vegetable.plantDiseaseSource
                            )
                            finish()
                            this.startActivity(intent)
                        } else {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        } catch (e: Exception) {
            ToastDisplay.displayToastWithMessage(this, e.message.toString())
        }

    }


    private fun checkInputs() {
        selectedImageFile?.let {
            val imageMultipart = MultipartBody.Part.createFormData(
                "image", it.name, RequestBody.create(MediaType.parse("image/*"), it)
            )
            uploadListener(imageMultipart)
        }
    }

    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                selectedImageFile = FileHelper.reduceFileImage(File(currentPhotoPath))
                if (selectedImageFile != null) {
                    rotateFile(selectedImageFile!!, true)
                    val bitmapImage: Bitmap = BitmapFactory.decodeFile(selectedImageFile!!.path)
                    val nh: Int = ((bitmapImage.height * (512.0 / bitmapImage.width)).toInt())
                    val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
                    imageResult.setImageBitmap(scaled)
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun checkIsProcessImage(): Boolean {
        if (selectedImageFile != null) {
            return true
        }
        return false
    }

    private fun removeImageResult() {
        imageResult.setImageDrawable(null)
        selectedImageFile = null
    }

    private fun showLoading(state: Boolean) {
        loadingLayout.visibility = if (state) View.VISIBLE else View.GONE
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

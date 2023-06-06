package com.dicoding.vegefinder

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class CameraActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var button: Button
    val REQUEST_IMAGE_CAPTURE = 1000
    lateinit var capturedImageBitmap: Bitmap
    lateinit var viewResultButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageView = findViewById(R.id.Image_Save)
        button = findViewById(R.id.btn_take_picture)
        viewResultButton = findViewById(R.id.view_result_button)
        viewResultButton.visibility = View.GONE
        button.visibility = View.VISIBLE

        button.setOnClickListener{

            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,"Error: " + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        viewResultButton.setOnClickListener {
            Toast.makeText(this, "Menampilkan hasil gambar...", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            viewResultButton.visibility = View.VISIBLE
            button.visibility = View.GONE

        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
package com.bangkit.vegefinder.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.vegefinder.ui.vegetable.CameraActivity
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.databinding.ActivityMainBinding
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.ui.auth.LoginActivity
import com.bangkit.vegefinder.ui.explore.Explore
import com.bangkit.vegefinder.ui.main.home.Home
import com.bangkit.vegefinder.ui.profile.ProfileActivity
import com.bangkit.vegefinder.ui.saved.SavedFragment
import com.bangkit.vegefinder.ui.utils.UtilDevelopmentFragment
import com.bangkit.vegefinder.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userNameTextView: TextView
    private lateinit var appBarView: View


//    companion object {
//        const val CAMERA_X_RESULT = 200
//
//        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//        private const val REQUEST_CODE_PERMISSIONS = 10
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(
//                    this,
//                    "Tidak mendapatkan permission.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }

//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        appBarView = findViewById(R.id.appBarLayout)
        userNameTextView = appBarView.findViewById(R.id.tv_user_name)

        val profileImage = findViewById<ImageView>(R.id.app_profile)
        val scanButton = findViewById<FloatingActionButton>(R.id.scan_button)
        scanButton.setOnClickListener {
            val i = Intent(this, CameraActivity::class.java)
            startActivity(i)
        }

        profileImage.setOnClickListener {
            Intent(this, ProfileActivity::class.java).also {
                startActivity(it)
            }
        }

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        val sessionManager = SessionManager(this@MainActivity)

        userViewModel.setUser()
        userViewModel.getUserResponse().observe(this) { response ->
            if (response != null && response.apiToken != null) {
                userNameTextView.text = "Hello, ${response.name}"
                sessionManager.saveUser(response)
            } else {
                ToastDisplay.displayToastWithMessage(this, "Session has been expired!")
                sessionManager.clearSession()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
        }


//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                this,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home())
                R.id.explore -> replaceFragment(Explore())
                R.id.garden -> replaceFragment(UtilDevelopmentFragment())
                R.id.saved -> replaceFragment(SavedFragment())
            }
            true
        }

        val fragmentKey = intent.getStringExtra("FRAGMENT_KEY")
        if (fragmentKey == "saved") {
            replaceFragment(SavedFragment())
            binding.bottomNavigationView.menu.findItem(R.id.saved).isChecked = true
        }
    }

    override fun onResume() {
        super.onResume()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        val fragmentKey = intent.getStringExtra("FRAGMENT_KEY")
        if (fragmentKey == "saved") {
            replaceFragment(SavedFragment())
            binding.bottomNavigationView.menu.findItem(R.id.saved).isChecked = true
            intent.removeExtra("FRAGMENT_KEY")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                Intent(this, ProfileActivity::class.java).also {
                    startActivity(it)
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
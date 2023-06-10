package com.dicoding.vegefinder.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.vegefinder.LoginActivity
import com.dicoding.vegefinder.MainActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.SessionManager
import com.dicoding.vegefinder.data.model.User
import com.dicoding.vegefinder.viewmodel.LogoutViewModel
import com.dicoding.vegefinder.viewmodel.UserViewModel
import com.dicoding.vegefinder.viewmodel.VegetableViewModel
import kotlin.system.exitProcess

class ProfileActivity : AppCompatActivity() {

    private lateinit var modeSwitch: SwitchCompat
    private lateinit var sessionManager: SessionManager
    private lateinit var logoutViewModel: LogoutViewModel
    private lateinit var userViewModel: UserViewModel
    private var nightMode: Boolean = false
    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        sessionManager = SessionManager(this)

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        logoutViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LogoutViewModel::class.java]

        val profileImageView: ImageView = findViewById(R.id.iv_profile)
        val nameTextView: TextView = findViewById(R.id.tv_name)
        val emailTextView: TextView = findViewById(R.id.tv_email)
        val savedTextView: TextView = findViewById(R.id.tv_saved)
        val historyTextView: TextView = findViewById(R.id.tv_history)
        val themeTextView: TextView = findViewById(R.id.tv_theme)
        val aboutTextView: TextView = findViewById(R.id.tv_about)
        val instagramButton: ImageButton = findViewById(R.id.ib_instagram)
        val linkedinButton: ImageButton = findViewById(R.id.ib_linkedin)
        val websiteButton: ImageButton = findViewById(R.id.ib_website)
        val logOutButton: TextView = findViewById(R.id.tv_logout)

        val currentUser: User? = sessionManager.getUser()
        Log.d("EXPLORE TEXT", "Vegetable List: $currentUser")

        if(currentUser != null){
            nameTextView.text = currentUser.name
            emailTextView.text = currentUser.email
            Glide.with(this)
                .asBitmap()
                .load("https://storage.googleapis.com/vegefinder-bucket/${currentUser.avatar}")
                .centerCrop()
                .placeholder(R.drawable.vegefinder)
                .into(profileImageView)
        }else{
            userViewModel.setUser()
            userViewModel.getUserResponse().observe(this) { user ->
                if (user != null) {
                    nameTextView.text = user.name
                    emailTextView.text = user.email
                    Glide.with(this)
                        .asBitmap()
                        .load("https://storage.googleapis.com/vegefinder-bucket/${user.avatar}")
                        .centerCrop()
                        .placeholder(R.drawable.vegefinder)
                        .into(profileImageView)
                    sessionManager.saveUser(user)
                }
            }

        }

        savedTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("FRAGMENT_KEY", "saved")
            startActivity(intent)
        }

        historyTextView.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        themeTextView.setOnClickListener {

        }

        aboutTextView.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        instagramButton.setOnClickListener {
            val instagramIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
            startActivity(instagramIntent)
        }

        linkedinButton.setOnClickListener {
            val linkedinIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/"))
            startActivity(linkedinIntent)
        }

        websiteButton.setOnClickListener {
            val websiteIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bilibili.tv/id/anime"))
            startActivity(websiteIntent)
        }

        modeSwitch = findViewById(R.id.switch_mode)
        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("night", false)!!

        if (nightMode) {
            modeSwitch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        modeSwitch.setOnCheckedChangeListener { _, _ ->
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", true)
            }
            editor?.apply()
        }

        logOutButton.setOnClickListener {
            logoutViewModel.logout()
            logoutViewModel.getLogoutResponse().observe(this) { response ->
                if (response != null) {
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    sessionManager.clearSession()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }else{
                    sessionManager.clearSession()
                    Toast.makeText(this, "logout error", Toast.LENGTH_SHORT).show()
                }
            }

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

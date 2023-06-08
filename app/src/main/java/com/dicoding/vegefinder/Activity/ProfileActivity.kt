package com.dicoding.vegefinder.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.dicoding.vegefinder.LoginActivity
import com.dicoding.vegefinder.MainActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.SessionManager

class ProfileActivity : AppCompatActivity() {

    private lateinit var modeSwitch: SwitchCompat
    private lateinit var sessionManager: SessionManager
    private var nightMode:Boolean=false
    private var editor: SharedPreferences.Editor?=null
    private var sharedPreferences:SharedPreferences?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        sessionManager = SessionManager(this)

        val savedTextView: TextView = findViewById(R.id.tv_saved)
        val historyTextView: TextView = findViewById(R.id.tv_history)
        val themeTextView: TextView = findViewById(R.id.tv_theme)
        val aboutTextView: TextView = findViewById(R.id.tv_about)
        val instagramButton: ImageButton = findViewById(R.id.ib_instagram)
        val linkedinButton: ImageButton = findViewById(R.id.ib_linkedin)
        val websiteButton: ImageButton = findViewById(R.id.ib_website)
        val logOutButton: TextView = findViewById(R.id.tv_logout)



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
            val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/"))
            startActivity(instagramIntent)
        }

        linkedinButton.setOnClickListener {
            val linkedinIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/"))
            startActivity(linkedinIntent)
        }

        websiteButton.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bilibili.tv/id/anime"))
            startActivity(websiteIntent)
        }

        modeSwitch = findViewById(R.id.switch_mode)
        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE)
        nightMode = sharedPreferences?.getBoolean("night", false)!!

        if (nightMode){
            modeSwitch.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        modeSwitch.setOnCheckedChangeListener { _, _ ->
            if(nightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", false)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night",true)
            }
            editor?.apply()
        }

        logOutButton.setOnClickListener{
            sessionManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
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

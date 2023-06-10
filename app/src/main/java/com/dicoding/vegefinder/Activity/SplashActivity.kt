package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.dicoding.vegefinder.LoginActivity
import com.dicoding.vegefinder.MainActivity
import com.dicoding.vegefinder.R
import com.dicoding.vegefinder.SessionManager

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        sessionManager = SessionManager(this)

        val nightMode = getSharedPreferences("MODE", Context.MODE_PRIVATE)
            .getBoolean("night", false)

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        lifecycleScope.launch {
            delay(700)
            val intent = Intent()
            val userToken = sessionManager.getUserToken()

            if(!userToken.isNullOrEmpty()) {
                intent.setClass(this@SplashActivity, MainActivity::class.java)
            } else {
                intent.setClass(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
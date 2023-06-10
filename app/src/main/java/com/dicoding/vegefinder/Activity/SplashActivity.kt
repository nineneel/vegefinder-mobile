package com.dicoding.vegefinder.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
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

        lifecycleScope.launch {
            delay(1500)
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
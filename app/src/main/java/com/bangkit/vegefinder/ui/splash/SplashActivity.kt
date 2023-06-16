package com.bangkit.vegefinder.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.bangkit.vegefinder.ui.auth.LoginActivity
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.ui.main.MainActivity

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        sessionManager = SessionManager(this)

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
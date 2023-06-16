package com.bangkit.vegefinder.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bangkit.vegefinder.R
import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.helper.ToastDisplay
import com.bangkit.vegefinder.ui.auth.LoginActivity
import com.bangkit.vegefinder.ui.history.HistoryActivity
import com.bangkit.vegefinder.ui.main.MainActivity
import com.bangkit.vegefinder.viewmodel.LogoutViewModel
import com.bangkit.vegefinder.viewmodel.UserViewModel
import com.bumptech.glide.Glide

class ProfileActivity : AppCompatActivity() {

    private lateinit var modeSwitch: SwitchCompat
    private lateinit var sessionManager: SessionManager
    private lateinit var logoutViewModel: LogoutViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var loadingLayout: View
    private var nightMode: Boolean = false
    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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
//        val themeTextView: TextView = findViewById(R.id.tv_theme)
        val aboutTextView: TextView = findViewById(R.id.tv_about)
        val instagramButton: ImageButton = findViewById(R.id.ib_instagram)
        val linkedinButton: ImageButton = findViewById(R.id.ib_linkedin)
        val websiteButton: ImageButton = findViewById(R.id.ib_website)
        val logOutButton: TextView = findViewById(R.id.tv_logout)

        loadingLayout = findViewById(R.id.loading_layout)

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
            }else{
                ToastDisplay.displayToastWithMessage(this, "Can't fetch user data!")
            }
        }

        savedTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("FRAGMENT_KEY", "saved")
            startActivity(intent)
            finishAffinity()
        }

        historyTextView.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
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

        logOutButton.setOnClickListener {
            showLoading(true)
            logoutViewModel.logout()
            logoutViewModel.getLogoutResponse().observe(this) { response ->
                showLoading(false)
                if (response != null) {
                    ToastDisplay.displayToastWithMessage(this, response.message)
                } else {
                    ToastDisplay.displayToastWithMessage(this, "Logout Error")
                }
                sessionManager.clearSession()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
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


    private fun showLoading(state: Boolean) {
        loadingLayout.visibility = if (state) View.VISIBLE else View.GONE
    }
}

package com.dicoding.vegefinder


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.vegefinder.Adapter.AvatarAdapter
import com.dicoding.vegefinder.data.model.Avatar
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.RegisterResponse
import com.dicoding.vegefinder.databinding.ActivityRegisterBinding
import com.dicoding.vegefinder.viewmodel.AvatarViewModel
import com.dicoding.vegefinder.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin: TextView
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var avatarViewModel: AvatarViewModel
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText
    private lateinit var rvAvatars: RecyclerView
    private lateinit var loadingLayout: View
    private lateinit var btnRegis: Button
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var avatarAdapter: AvatarAdapter
    private lateinit var sessionManager: SessionManager

    private var errorDialog: AlertDialog? = null
    private var avatarId: Int? = null

    private var totalResponse: Int = 0
    private var currentResponse: Int = 0


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register"

        sessionManager = SessionManager(this)

        etName = findViewById(R.id.register_name)
        etEmail = findViewById(R.id.register_email)
        etPassword = findViewById(R.id.register_password)
        etRePassword = findViewById(R.id.valid_password)
        btnRegis = findViewById(R.id.btn_regis)
        loadingLayout = findViewById(R.id.loading_layout)

        avatarAdapter = AvatarAdapter { id ->
            avatarId = id
            Log.d("REGISTER", "TEST ID: $id")
        }
        avatarAdapter.notifyDataSetChanged()

        avatarViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AvatarViewModel::class.java]

        rvAvatars = findViewById(R.id.rv_avatars)
        rvAvatars.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAvatars.adapter = avatarAdapter

        val avatarList: ArrayList<Avatar> = sessionManager.getAvatarList()

        if(avatarList.size > 0){
            avatarAdapter.setAvatarList(avatarList)
        }

        avatarViewModel.setAvatars()
        avatarViewModel.getAvatarsResponse().observe(this) { response ->
            if (response != null) {
                if(avatarList.size == 0){
                    avatarAdapter.setAvatarList(response)
                    sessionManager.saveAvatarList(response)
                }else if(avatarList.size < response.size){
                    sessionManager.removeByKey("avatarList")
                    sessionManager.saveAvatarList(response)
                }
            }
        }




        registerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RegisterViewModel::class.java]

        btnRegis.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rePassword = etRePassword.text.toString().trim()

            Log.d("REGISTER", "TEST ID ONBUTTON: $avatarId")

            if (name.isEmpty()) {
                etName.error = "Name cannot be empty"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                etEmail.error = "Email cannot be empty"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password cannot be empty"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (rePassword.isEmpty()) {
                etRePassword.error = "Confirmation Password cannot be empty"
                etRePassword.requestFocus()
                return@setOnClickListener
            }

            if (password != rePassword) {
                etRePassword.error = "Password and confirmation password didn't match"
                etRePassword.requestFocus()
                return@setOnClickListener
            }

            if (avatarId == null) {
                etRePassword.error = "Need avatar Id"
                etRePassword.requestFocus()
                return@setOnClickListener
            }

            val data = RegisterRequest(
                binding.registerName.text.toString(),
                binding.registerEmail.text.toString(),
                binding.registerPassword.text.toString(),
                binding.validPassword.text.toString(),
                avatarId.toString()
            )

            showLoading(true)

            registerViewModel.register(data)
            registerViewModel.getResponse().observe(this) { response ->
                Log.d("Register", "test response $response")

                if (totalResponse <= currentResponse) {
                    if (response != null) {
                        if (response.status == "success") {
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            val emailError = response.errors?.email?.getOrNull(0)
                            val passwordError = response.errors?.password?.getOrNull(0)

                            if (emailError != null) {
                                etEmail.error = emailError
                                etEmail.requestFocus()
                            }

                            if (passwordError != null) {
                                etPassword.error = passwordError
                                etPassword.requestFocus()
                            }
                        }
                    }
                    showLoading(false)
                    totalResponse++
                    currentResponse = 0
                }
                currentResponse++
            }

        }

        btnLogin = findViewById(R.id.signIn)
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
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
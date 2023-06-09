package com.dicoding.vegefinder


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
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.RegisterResponse
import com.dicoding.vegefinder.databinding.ActivityRegisterBinding
import com.dicoding.vegefinder.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin: TextView
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etRePassword: EditText
    private lateinit var btnRegis: Button
    private lateinit var binding: ActivityRegisterBinding

    private var errorDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register"

        etName = findViewById(R.id.register_name)
        etEmail = findViewById(R.id.register_email)
        etPassword = findViewById(R.id.register_password)
        etRePassword = findViewById(R.id.valid_password)
        btnRegis = findViewById(R.id.btn_regis)

        registerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RegisterViewModel::class.java]

        btnRegis.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val rePassword = etRePassword.text.toString().trim()

            if (name.isEmpty()) {
                etName.error = "Name cannot be empty"
                etName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
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

            if(password != rePassword){
                etRePassword.error = "Password and confirmation password didn't match"
                etRePassword.requestFocus()
                return@setOnClickListener
            }


            val avatarId = (1..4).random()

            val data = RegisterRequest(
                binding.registerName.text.toString(),
                binding.registerEmail.text.toString(),
                binding.registerPassword.text.toString(),
                binding.validPassword.text.toString(),
                avatarId.toString()
            )

            showLoading(true)

            registerViewModel.register(data)
            registerViewModel.getResponse().observe(this){ response ->
                if (response!=null){
                    if(response.status == "success"){
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }else{
                        showLoading(false)
                        val emailError = response.errors?.email?.getOrNull(0)
                        val passwordError = response.errors?.password?.getOrNull(0)

                        if(emailError != null){
                            etEmail.error = emailError
                            etEmail.requestFocus()
                            return@observe
                        }

                        if(passwordError != null){
                            etPassword.error = passwordError
                            etPassword.requestFocus()
                            return@observe
                        }
                    }
                }
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
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
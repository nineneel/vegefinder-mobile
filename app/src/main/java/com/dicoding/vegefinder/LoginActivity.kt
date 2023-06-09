package com.dicoding.vegefinder

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.vegefinder.data.model.User
import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.databinding.ActivityLoginBinding
import com.dicoding.vegefinder.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        loginViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[LoginViewModel::class.java]

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Username cannot be empty"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password cannot be empty"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            val data = LoginRequest(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )

            showLoading(true)
            loginViewModel.login(data)
            loginViewModel.getLoginResponse().observe(this) { response ->
                if (response != null) {

                    if (response.status == "success") {
                        sessionManager.saveAuthToken(response.token)
                        sessionManager.setLogin(true)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val status = response.status
                        val errorMessage = response.message

                        if (status == "failed") {
                            etEmail.error = errorMessage
                            etEmail.requestFocus()
                            return@observe
                        } else {
                            val emailError = response.errors?.email?.getOrNull(0)
                            val passwordError = response.errors?.password?.getOrNull(0)

                            if (emailError != null) {
                                etEmail.error = emailError
                                etEmail.requestFocus()
                                return@observe
                            }

                            if (passwordError != null) {
                                etPassword.error = passwordError
                                etPassword.requestFocus()
                                return@observe
                            }
                        }
                    }
                }
                showLoading(false)
            }
        }

        btnRegister = findViewById(R.id.sigUp)
        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
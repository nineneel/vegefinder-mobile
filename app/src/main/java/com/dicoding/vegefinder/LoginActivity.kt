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
import com.dicoding.vegefinder.data.Result

import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var loadingLayout: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    private var totalResponse : Int = 0
    private var currentResponse: Int = 0

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
        loadingLayout = findViewById(R.id.loading_layout)

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

            showLoading(true)

            val data = LoginRequest(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )

            loginViewModel.login(data).observe(this){result ->
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Success -> {
                        showLoading(false)
                        val response = result.data
                        if(response != null){
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
                        }else{
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
        }

        btnRegister = findViewById(R.id.sigUp)
        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }


    private fun showLoading(state: Boolean) {
        loadingLayout.visibility = if (state) View.VISIBLE else View.GONE
    }
}
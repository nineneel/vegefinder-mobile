package com.dicoding.vegefinder

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.databinding.ActivityLoginBinding
import com.dicoding.vegefinder.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
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

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)

        etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty()) {
                etUsername.error = "Username cannot be empty"
                etUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password cannot be empty"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                etPassword.error = "Password must be at least 8 characters"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            sessionManager.saveAuthToken("dummy_token")
            val data = LoginRequest(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )

            loginViewModel.login(data)
            loginViewModel.getSearchUsers().observe(this) {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                if (it == false) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }
            }
        }

        btnRegister = findViewById(R.id.sigUp)
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
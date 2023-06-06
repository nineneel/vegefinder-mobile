package com.dicoding.vegefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.databinding.ActivityRegisterBinding
import com.dicoding.vegefinder.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnLogin: TextView
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register"

        val username: EditText = findViewById(R.id.register_email)
        val regBtn: MaterialButton = findViewById(R.id.btn_regis)

        registerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[RegisterViewModel::class.java]

        binding.btnRegis.setOnClickListener {
            val username1 = username.text.toString()
            val data = RegisterRequest(
                binding.registerName.text.toString(),
                binding.registerEmail.text.toString(),
                binding.registerPassword.text.toString()
            )
            registerViewModel.register(data)
            registerViewModel.getSearchUsers().observe(this){
//                Toast.makeText(this@RegisterActivity, it.toString(), Toast.LENGTH_SHORT).show()
                if (it==false){
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                }
            }
        }

        btnLogin = findViewById(R.id.signIn)
        btnLogin.setOnClickListener {
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
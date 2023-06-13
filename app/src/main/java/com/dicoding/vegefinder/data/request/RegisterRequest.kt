package com.dicoding.vegefinder.data.request

data class RegisterRequest(
    val name : String,
    val email: String,
    val password: String,
    val password_confirmation: String,
    val avatar: String
)


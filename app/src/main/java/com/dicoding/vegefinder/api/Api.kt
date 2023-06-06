package com.dicoding.vegefinder.api


import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.LoginResponse
import com.dicoding.vegefinder.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
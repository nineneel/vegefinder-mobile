package com.dicoding.vegefinder.api


import com.dicoding.vegefinder.SessionManager
import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.JenisResponse
import com.dicoding.vegefinder.data.response.LoginResponse
import com.dicoding.vegefinder.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("register")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("home/types")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getHomeTypes(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<JenisResponse>
}
package com.dicoding.vegefinder.api


import com.dicoding.vegefinder.SessionManager
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.data.model.VegetableType
import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.data.request.PredictRequest
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.LoginResponse
import com.dicoding.vegefinder.data.response.PredictResponse
import com.dicoding.vegefinder.data.response.RegisterResponse
import okhttp3.MultipartBody
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
    ): Call<ArrayList<VegetableType>>

    @GET("vegetables")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getAllVegetable(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<Vegetable>>

    @Multipart
    @POST("predict")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getPredict(
        @Header("Authorization") token: String = "Bearer ${SessionManager.userToken}",
        @Part image: MultipartBody.Part,
    ): Call<PredictResponse>

}
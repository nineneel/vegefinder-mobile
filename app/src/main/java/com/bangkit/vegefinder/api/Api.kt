package com.bangkit.vegefinder.api


import com.bangkit.vegefinder.data.preferences.SessionManager
import com.bangkit.vegefinder.data.model.Avatar
import com.bangkit.vegefinder.data.model.User
import com.bangkit.vegefinder.data.model.Vegetable
import com.bangkit.vegefinder.data.model.VegetableType
import com.bangkit.vegefinder.data.request.LoginRequest
import com.bangkit.vegefinder.data.request.RegisterRequest
import com.bangkit.vegefinder.data.response.LoginResponse
import com.bangkit.vegefinder.data.response.LogoutResponse
import com.bangkit.vegefinder.data.response.PredictResponse
import com.bangkit.vegefinder.data.response.RegisterResponse
import com.bangkit.vegefinder.data.response.SaveVegetableResponse
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

    @GET("avatars")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getAvatars(): Call<ArrayList<Avatar>>

    @GET("user")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getUser(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<User>

    @GET("home/types")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getHomeTypes(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<VegetableType>>

    @GET("home/histories")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getHomeHistories(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<Vegetable>>

    @GET("vegetables")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getAllVegetable(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<Vegetable>>

    @POST("vegetables/{id}/save")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun saveVegetable(
        @Path("id") id: Int,
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<SaveVegetableResponse>

    @Multipart
    @POST("predict")
    fun getPredict(
        @Part image: MultipartBody.Part,
        @Part("user_id") user_id: Int
    ): Call<PredictResponse>

    @GET("saveds")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getSavedVegetable(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<Vegetable>>


    @GET("histories")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getAllHistories(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<Vegetable>>

    @GET("types")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun getAllTypes(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<ArrayList<VegetableType>>


    @POST("logout")
    @Headers("X-Requested-With: XMLHttpRequest")
    fun logout(
        @Header("Authorization") token : String = "Bearer ${SessionManager.userToken}"
    ): Call<LogoutResponse>

}
package com.bangkit.vegefinder.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientPredict {
//    private const val BASE_URL = "http://35.202.36.69/api/v1/"
//    private const val BASE_URL = "http://192.168.0.103:8080/"
//    private const val BASE_URL = "https://vegefinder-machine-learning-pl6a2qwedq-et.a.run.app/"
    private const val BASE_URL = "https://vegefinder-machine-learning-2-pl6a2qwedq-et.a.run.app/"
//
    private var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstance: Api = retrofit.create(Api::class.java)
}
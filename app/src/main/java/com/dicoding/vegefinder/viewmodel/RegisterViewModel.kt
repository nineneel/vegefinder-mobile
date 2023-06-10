package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.request.RegisterRequest
import com.dicoding.vegefinder.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    val registerResponse = MutableLiveData<RegisterResponse>()

    fun register(registerRequest: RegisterRequest) {

        RetrofitClient.apiInstance
            .register(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val statusCode = response.code()
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)

                    if (response.isSuccessful) {
                        registerResponse.postValue(response.body())
                    }else {
                        registerResponse.postValue(errorResponse)
                    }

                    Log.d("Register", "Failuer top $errorResponse with code $statusCode")

                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d("Register", "Failuer ${t.message}")
                    registerResponse.postValue(null)
                }

            })

    }

    fun getResponse(): LiveData<RegisterResponse> {
        return registerResponse
    }
}
package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.request.LoginRequest
import com.dicoding.vegefinder.data.response.LoginResponse
import com.dicoding.vegefinder.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    val loginResponse = MutableLiveData<LoginResponse>()

    fun login(loginRequest: LoginRequest) {
        RetrofitClient.apiInstance
            .login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val statusCode = response.code()

                    if (response.isSuccessful) {
                        loginResponse.postValue(response.body())
                    }else{
                        if(statusCode == 400){
                            loginResponse.postValue(response.body())
                        }else{
                            if(statusCode == 422){
                                val errorBody = response.errorBody()?.string()
                                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                                loginResponse.postValue(errorResponse)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginResponse.postValue(null)
                    Log.v("Failure", "testt ->> " + t.message.toString())
                }
            })
    }

    fun getLoginResponse(): LiveData<LoginResponse> {
        return loginResponse
    }
}
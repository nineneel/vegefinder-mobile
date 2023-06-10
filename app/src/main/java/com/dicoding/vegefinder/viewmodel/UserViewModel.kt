package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.model.User
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.data.response.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    val userResponse = MutableLiveData<User?>()

    fun setUser(){
        RetrofitClient.apiInstance
            .getUser()
            .enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ){
                    val statusCode = response.code()
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, User::class.java)

                    if (response.isSuccessful) {
                        userResponse.postValue(response.body())
                    }else{
                        userResponse.postValue(errorResponse)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    userResponse.postValue(null)
                }
            })
    }

    fun getUserResponse(): LiveData<User?> {
        return userResponse
    }
}
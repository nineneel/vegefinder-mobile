package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.model.User
import com.dicoding.vegefinder.data.model.Vegetable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    val userResponse = MutableLiveData<User>()

    fun setUser(){
        RetrofitClient.apiInstance
            .getUser()
            .enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ){
                    if (response.isSuccessful) {
                        userResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.v("Failure", "testt ->> ${t.message.toString()}")
                    userResponse.postValue(null)
                }
            })
    }

    fun getUserResponse(): LiveData<User> {
        return userResponse
    }
}
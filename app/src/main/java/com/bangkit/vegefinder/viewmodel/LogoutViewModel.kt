package com.bangkit.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.response.LogoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoutViewModel: ViewModel() {

    val logoutResponse = MutableLiveData<LogoutResponse?>()

    fun logout() {
        RetrofitClient.apiInstance
            .logout()
            .enqueue(object : Callback<LogoutResponse> {
                override fun onResponse(
                    call: Call<LogoutResponse>,
                    response: Response<LogoutResponse>
                ) {
                    if (response.isSuccessful) {
                        logoutResponse.postValue(response.body())
                    }else{
                        logoutResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                    logoutResponse.postValue(null)
                }
            })
    }

    fun getLogoutResponse(): LiveData<LogoutResponse?> {
        return logoutResponse
    }
}
package com.bangkit.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.vegefinder.data.Result
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.request.LoginRequest
import com.bangkit.vegefinder.data.response.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun login(loginRequest: LoginRequest) : LiveData<Result<LoginResponse?>> = liveData {
        emit(Result.Loading)
        val returnValue = MutableLiveData<Result<LoginResponse?>>()
        var returnErrorValue: String? = null

        RetrofitClient.apiInstance
            .login(loginRequest)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)

                    if (response.isSuccessful) {
                        returnValue.value = Result.Success(response.body())
                    }else{
                        returnValue.value = Result.Success(errorResponse)
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    returnErrorValue = t.message.toString()
                }
            })

        if(returnErrorValue != null){
            emit(Result.Error(returnErrorValue!!))
        }else{
            emitSource(returnValue)
        }
    }
}
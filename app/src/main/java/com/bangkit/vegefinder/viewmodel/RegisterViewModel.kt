package com.bangkit.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.Result
import com.bangkit.vegefinder.data.request.RegisterRequest
import com.bangkit.vegefinder.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {


    fun register(registerRequest: RegisterRequest): LiveData<Result<RegisterResponse?>> = liveData {
        emit(Result.Loading)

        val returnValue = MutableLiveData<Result<RegisterResponse?>>()
        var returnErrorValue: String? = null

        RetrofitClient.apiInstance
            .register(registerRequest)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)

                    if (response.isSuccessful) {
                        returnValue.value = Result.Success(response.body())
                    } else {
                        returnValue.value = Result.Success(errorResponse)
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
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
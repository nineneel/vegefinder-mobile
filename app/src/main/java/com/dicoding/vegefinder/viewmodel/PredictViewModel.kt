package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dicoding.vegefinder.api.RetrofitClientPredict
import com.dicoding.vegefinder.data.Result
import com.dicoding.vegefinder.data.response.LoginResponse
import com.dicoding.vegefinder.data.response.PredictResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel: ViewModel() {
    fun predict(image: MultipartBody.Part, user_id: Int) : LiveData<Result<PredictResponse?>> = liveData {
        emit(Result.Loading)
        val returnValue = MutableLiveData<Result<PredictResponse?>>()
        var returnErrorValue: String? = null

        RetrofitClientPredict.apiInstance
            .getPredict(image = image, user_id = user_id)
            .enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
                    if (response.isSuccessful) {
                        returnValue.value = Result.Success(response.body())
                    }else{
                        returnValue.value = Result.Success(errorResponse)
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    returnErrorValue = t.message.toString()
                }
            })

        if(returnErrorValue != null){
            emit(Result.Error(returnErrorValue!!))
        }

        emitSource(returnValue)
    }
}
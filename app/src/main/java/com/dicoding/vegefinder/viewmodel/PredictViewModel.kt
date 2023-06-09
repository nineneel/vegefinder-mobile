package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel: ViewModel() {

    val predictVegetableResponse = MutableLiveData<PredictResponse>()

    fun sendVegetableImage(image: MultipartBody.Part) {
        RetrofitClient.apiInstance
            .getPredict(image = image)
            .enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    val statusCode = response.code()
                    val errorBody = response.errorBody()?.string()
                    Log.v("CAMERA CHECK", "status code $statusCode with ${errorBody}")
                    if (response.isSuccessful) {
                        predictVegetableResponse.postValue(response.body())
                    }else{
                        predictVegetableResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    Log.v("CAMERA CHECK", "testt ->> " + t.message.toString())
                    predictVegetableResponse.postValue(null)
                }
            })
    }

    fun getPredictResponse(): LiveData<PredictResponse> {
        return predictVegetableResponse
    }
}
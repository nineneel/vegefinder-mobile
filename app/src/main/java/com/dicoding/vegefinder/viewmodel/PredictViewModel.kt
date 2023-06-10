package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClientPredict
import com.dicoding.vegefinder.data.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel: ViewModel() {

    val predictVegetableResponse = MutableLiveData<PredictResponse?>()

    fun sendVegetableImage(image: MultipartBody.Part, user_id: Int) {
        RetrofitClientPredict.apiInstance
            .getPredict(image = image, user_id = user_id)
            .enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    val statusCode = response.code()
                    val errorBody = response.errorBody()?.string()
                    Log.v("CAMERA CHECK", "status code $statusCode with $errorBody")
                    if (response.isSuccessful) {
                        predictVegetableResponse.postValue(response.body())
                    }else{
                        Log.v("CAMERA CHECK", "Test ->> $statusCode")
                        predictVegetableResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    Log.v("CAMERA CHECK", "testt ->> " + t.message.toString())
                    predictVegetableResponse.postValue(null)
                }
            })
    }

    fun getPredictResponse(): MutableLiveData<PredictResponse?> {
        return predictVegetableResponse
    }
}
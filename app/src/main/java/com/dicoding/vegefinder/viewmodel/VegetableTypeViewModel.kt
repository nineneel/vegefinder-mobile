package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.model.VegetableType
import com.dicoding.vegefinder.data.response.VegetableTypeResponse
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class VegetableTypeViewModel: ViewModel() {
    val vegetableTypeResponse = MutableLiveData<ArrayList<VegetableType>>()

    fun setVegetableType(){
        RetrofitClient.apiInstance
            .getHomeTypes()
            .enqueue(object : Callback<VegetableTypeResponse>{
                override fun onResponse(
                    call: Call<VegetableTypeResponse>,
                    response: Response<VegetableTypeResponse>
                ){
                    Log.v("Failure", "testt is masuk ${response.code()}")
                    if (response.isSuccessful) {
                        vegetableTypeResponse.postValue(response.body()?.types)
                    }
                }

                override fun onFailure(call: Call<VegetableTypeResponse>, t: Throwable) {
                    Log.v("Failure", "testt ->> ${t.message.toString()}")
                    vegetableTypeResponse.postValue(null)
                }
            })
    }

    fun getVegetableTypeResponse(): LiveData<ArrayList<VegetableType>>{
        return vegetableTypeResponse
    }
}
package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.model.Vegetable
import com.dicoding.vegefinder.data.model.VegetableType
import com.dicoding.vegefinder.data.response.LoginResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VegetableViewModel:ViewModel() {

    val vegetableResponse = MutableLiveData<ArrayList<Vegetable>>()

    fun setVegetable(){
        RetrofitClient.apiInstance
            .getAllVegetable()
            .enqueue(object : Callback<ArrayList<Vegetable>> {
                override fun onResponse(
                    call: Call<ArrayList<Vegetable>>,
                    response: Response<ArrayList<Vegetable>>
                ){
                    if (response.isSuccessful) {
                        vegetableResponse.postValue(response.body())
                    }else{
                        vegetableResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Vegetable>>, t: Throwable) {
                    Log.v("Failure", "testt ->> ${t.message.toString()}")
                    vegetableResponse.postValue(null)
                }
            })
    }

    fun getVegetableResponse(): LiveData<ArrayList<Vegetable>> {
        return vegetableResponse
    }
}
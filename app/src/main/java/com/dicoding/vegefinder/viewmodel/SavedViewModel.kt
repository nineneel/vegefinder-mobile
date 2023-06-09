package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.model.Vegetable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SavedViewModel: ViewModel() {
    val vegetableResponse = MutableLiveData<ArrayList<Vegetable>>()

    fun setVegetable(){
        RetrofitClient.apiInstance
            .getSavedVegetable()
            .enqueue(object : Callback<ArrayList<Vegetable>> {
                override fun onResponse(
                    call: Call<ArrayList<Vegetable>>,
                    response: Response<ArrayList<Vegetable>>
                ){
                    if (response.isSuccessful) {
                        vegetableResponse.postValue(response.body())
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
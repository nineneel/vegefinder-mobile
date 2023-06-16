package com.bangkit.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.model.VegetableType
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class HomeTypeViewModel: ViewModel() {
    val vegetableTypeResponse = MutableLiveData<ArrayList<VegetableType>?>()

    fun setVegetableType(){
        RetrofitClient.apiInstance
            .getHomeTypes()
            .enqueue(object : Callback<ArrayList<VegetableType>>{

                override fun onResponse(
                    call: Call<ArrayList<VegetableType>>,
                    response: Response<ArrayList<VegetableType>>
                ) {
                    if (response.isSuccessful) {
                        vegetableTypeResponse.postValue(response.body())
                    }else{
                        vegetableTypeResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<VegetableType>>, t: Throwable) {
                    vegetableTypeResponse.postValue(null)
                }
            })
    }

    fun getVegetableTypeResponse(): LiveData<ArrayList<VegetableType>?>{
        return vegetableTypeResponse
    }
}
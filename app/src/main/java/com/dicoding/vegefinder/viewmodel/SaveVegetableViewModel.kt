package com.dicoding.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.response.SaveVegetableResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaveVegetableViewModel: ViewModel() {

    val saveVegetableResponse = MutableLiveData<SaveVegetableResponse>()

    fun setSaveVegetable(id: Int){
        RetrofitClient.apiInstance
            .saveVegetable(id = id)
            .enqueue(object : Callback<SaveVegetableResponse>{
                override fun onResponse(
                    call: Call<SaveVegetableResponse>,
                    response: Response<SaveVegetableResponse>
                ) {
                    if(response.isSuccessful){
                        saveVegetableResponse.postValue(response.body())
                    }else{
                        saveVegetableResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<SaveVegetableResponse>, t: Throwable) {
                    saveVegetableResponse.postValue(null)
                }

            })
    }

    fun getSaveVegetableResponse(): LiveData<SaveVegetableResponse>{
        return saveVegetableResponse
    }
}
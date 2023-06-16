package com.bangkit.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.model.Vegetable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeHistoryViewModel: ViewModel() {
    val historiesResponse = MutableLiveData<ArrayList<Vegetable>?>()

    fun setHistory(){
        RetrofitClient.apiInstance
            .getHomeHistories()
            .enqueue(object : Callback<ArrayList<Vegetable>> {

                override fun onResponse(
                    call: Call<ArrayList<Vegetable>>,
                    response: Response<ArrayList<Vegetable>>
                ) {
                    if (response.isSuccessful) {
                        historiesResponse.postValue(response.body())
                    }else{
                        historiesResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Vegetable>>, t: Throwable) {
                    historiesResponse.postValue(null)
                }
            })
    }

    fun getHistoryResponse(): LiveData<ArrayList<Vegetable>?> {
        return historiesResponse
    }
}
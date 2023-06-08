package com.dicoding.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.vegefinder.Adapter.Jenis
import com.dicoding.vegefinder.api.RetrofitClient
import com.dicoding.vegefinder.data.response.JenisResponse
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class JenisViewModel: ViewModel() {
    val jenisResponse = MutableLiveData<ArrayList<Jenis>>()

    fun setJenis(){
        RetrofitClient.apiInstance
            .getHomeTypes()
            .enqueue(object : Callback<JenisResponse>{
                override fun onResponse(
                    call: Call<JenisResponse>,
                    response: Response<JenisResponse>
                ){
                    Log.v("Failure", "testt is masuk ${response.code()}")
                    if (response.isSuccessful) {
                        jenisResponse.postValue(response.body()?.types)
                    }
                }

                override fun onFailure(call: Call<JenisResponse>, t: Throwable) {
                    Log.v("Failure", "testt ->> ${t.message.toString()}")
                    jenisResponse.postValue(null)
                }
            })
    }

    fun getJenisResponse(): LiveData<ArrayList<Jenis>>{
        return jenisResponse
    }
}
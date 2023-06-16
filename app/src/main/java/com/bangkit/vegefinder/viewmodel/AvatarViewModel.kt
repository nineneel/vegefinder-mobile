package com.bangkit.vegefinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.vegefinder.api.RetrofitClient
import com.bangkit.vegefinder.data.model.Avatar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvatarViewModel: ViewModel() {

    val avatarResponse = MutableLiveData<ArrayList<Avatar>?>()

    fun setAvatars(){
        RetrofitClient.apiInstance
            .getAvatars()
            .enqueue(object : Callback<ArrayList<Avatar>> {
                override fun onResponse(
                    call: Call<ArrayList<Avatar>>,
                    response: Response<ArrayList<Avatar>>
                ){
                    if (response.isSuccessful) {
                        avatarResponse.postValue(response.body())
                    }else{
                        avatarResponse.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Avatar>>, t: Throwable) {
                    avatarResponse.postValue(null)
                }
            })
    }

    fun getAvatarsResponse(): LiveData<ArrayList<Avatar>?> {
        return avatarResponse
    }
}
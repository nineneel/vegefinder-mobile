package com.bangkit.vegefinder.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bangkit.vegefinder.api.RetrofitClientPredict
import com.bangkit.vegefinder.data.Result
import com.bangkit.vegefinder.data.response.PredictResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class PredictViewModel : ViewModel() {
    fun predict(image: MultipartBody.Part, user_id: Int): LiveData<Result<PredictResponse?>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)

            try {
                val response = RetrofitClientPredict.apiInstance
                    .getPredict(image = image, user_id = user_id)
                    .execute()

                val errorBody = response.errorBody()?.string()

                if (response.isSuccessful) {
                    emit(Result.Success(response.body()))
                } else {
                    emit(Result.Error(errorBody!!))
                }
            } catch (t: Throwable) {
                emit(Result.Error(t.message!!))
            }
        }
}


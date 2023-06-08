package com.dicoding.vegefinder.data.response

import com.google.gson.annotations.SerializedName

data class SaveVegetableResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String
)

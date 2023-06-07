package com.dicoding.vegefinder.data.response

import com.dicoding.vegefinder.data.model.VegetableType
import com.google.gson.annotations.SerializedName

data class VegetableTypeResponse (
    @SerializedName("types")
    val types: ArrayList<VegetableType>
)
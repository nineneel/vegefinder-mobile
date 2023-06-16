package com.bangkit.vegefinder.data.response

import com.bangkit.vegefinder.data.model.VegetableType
import com.google.gson.annotations.SerializedName

data class VegetableTypeResponse (
    @SerializedName("types")
    val types: ArrayList<VegetableType>
)
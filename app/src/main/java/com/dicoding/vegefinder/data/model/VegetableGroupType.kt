package com.dicoding.vegefinder.data.model

import com.google.gson.annotations.SerializedName

data class VegetableGroupType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
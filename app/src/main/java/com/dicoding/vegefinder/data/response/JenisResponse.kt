package com.dicoding.vegefinder.data.response

import com.dicoding.vegefinder.Adapter.Jenis
import com.google.gson.annotations.SerializedName

data class JenisResponse (
    @SerializedName("types")
    val types: ArrayList<Jenis>
)
package com.dicoding.vegefinder.data.model

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
)

package com.dicoding.vegefinder.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Result
)

data class Result(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("register_method")
    val register_method: String,
    @SerializedName("avatar_id")
    val avatar_id: Int,
    @SerializedName("updated_at")
    val updated_at: Int,
    @SerializedName("created_at")
    val created_at: Int,
)
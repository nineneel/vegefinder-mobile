package com.bangkit.vegefinder.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("result")
    val result: Result? = null,
    @SerializedName("errors")
    val errors: AuthError? = null
)

data class AuthError(
    @SerializedName("email")
    val email: ArrayList<String>? = null,
    @SerializedName("password")
    val password: ArrayList<String>? = null,
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
    val updated_at: String,
    @SerializedName("created_at")
    val created_at: String,
)
package com.bangkit.vegefinder.data.response

import com.bangkit.vegefinder.data.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: User? = null,
    @SerializedName("errors")
    val errors: AuthError? = null
)



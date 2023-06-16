package com.bangkit.vegefinder.data.response

import com.bangkit.vegefinder.data.model.Vegetable
import com.google.gson.annotations.SerializedName

data class PredictResponse(
	@SerializedName("status")
	val status: String,
	@SerializedName("vegetable")
	val vegetable: Vegetable? = null,
	@SerializedName("message")
	val message: String? = null,
	@SerializedName("is_auth")
	val isAuth: Boolean? = null,
	@SerializedName("probabilities")
	val probabilities: String? = null,
	@SerializedName("error")
	val error: String? = null
)
package com.bangkit.vegefinder.data.model

import com.google.gson.annotations.SerializedName

data class VegetableType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("type_group_id")
    val typeGroupId: Int,
    @SerializedName("type_group")
    val typeGroups: VegetableGroupType
)

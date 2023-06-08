package com.dicoding.vegefinder.Adapter

import com.google.gson.annotations.SerializedName

data class Jenis(
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
    val typeGroups: TypeGroup
)

data class TypeGroup(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
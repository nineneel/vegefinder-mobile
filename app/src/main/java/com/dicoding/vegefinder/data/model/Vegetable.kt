package com.dicoding.vegefinder.data.model

import com.google.gson.annotations.SerializedName


data class Vegetable(
        @SerializedName("id")
        val id: Int,
        @SerializedName("class_name")
        val className: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("other_name")
        val otherName: String,
        @SerializedName("thumbnail")
        val thumbnail: String,
        @SerializedName("images")
        val images: ArrayList<String>,
        @SerializedName("description")
        val description: String,
        @SerializedName("description_source")
        val descriptionSource: String,
        @SerializedName("how_to_plant")
        val howToPlant: String,
        @SerializedName("how_to_plant_source")
        val howToPlantSource: String,
        @SerializedName("plant_care")
        val plantCare: String,
        @SerializedName("plant_care_source")
        val plantCareSource: String,
        @SerializedName("plant_disease")
        val plantDisease: String,
        @SerializedName("plant_disease_source")
        val plantDiseaseSource: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("types")
        val types: ArrayList<VegetableType>
)

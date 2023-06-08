package com.dicoding.vegefinder.data.response

data class PredictResponse(
	val vegetable: Vegetable? = null,
	val status: String? = null
)

data class TypesItem(
	val name: String? = null,
	val id: Int? = null
)

data class Vegetable(
	val thumbnail: String? = null,
	val images: List<String?>? = null,
	val types: List<TypesItem?>? = null,
	val howToPlant: String? = null,
	val plantDisease: String? = null,
	val description: String? = null,
	val createdAt: String? = null,
	val updatedAt: String? = null,
	val descriptionSource: String? = null,
	val name: String? = null,
	val otherName: Any? = null,
	val howToPlantSource: String? = null,
	val id: Int? = null,
	val plantDiseaseSource: String? = null,
	val className: String? = null,
	val plantCare: String? = null,
	val plantCareSource: String? = null
)


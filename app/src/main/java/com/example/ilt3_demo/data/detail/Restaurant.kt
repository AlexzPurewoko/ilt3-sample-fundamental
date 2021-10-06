package com.example.ilt3_demo.data.detail

import com.google.gson.annotations.SerializedName

data class Restaurant(

    @field:SerializedName("customerReviews")
	val customerReviews: List<CustomerReviewItem>,

    @field:SerializedName("address")
	val address: String,

    @field:SerializedName("pictureId")
	val pictureId: String,

    @field:SerializedName("city")
	val city: String,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("rating")
	val rating: Double,

    @field:SerializedName("description")
	val description: String,

    @field:SerializedName("id")
	val id: String,

    @field:SerializedName("categories")
	val categories: List<CategoryItem>,

    @field:SerializedName("menus")
	val menus: Menus
)
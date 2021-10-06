package com.example.ilt3_demo.data.detail

import com.google.gson.annotations.SerializedName

data class RestaurantDetail(

	@field:SerializedName("restaurant")
	val restaurant: Restaurant,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
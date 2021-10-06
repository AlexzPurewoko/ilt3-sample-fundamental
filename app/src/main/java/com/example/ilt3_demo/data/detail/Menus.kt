package com.example.ilt3_demo.data.detail

import com.google.gson.annotations.SerializedName

data class Menus(

	@field:SerializedName("foods")
	val foods: List<FoodsItem>,

	@field:SerializedName("drinks")
	val drinks: List<DrinksItem>
)
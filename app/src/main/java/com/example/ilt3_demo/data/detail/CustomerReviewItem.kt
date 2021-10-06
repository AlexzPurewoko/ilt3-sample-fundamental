package com.example.ilt3_demo.data.detail

import com.google.gson.annotations.SerializedName

data class CustomerReviewItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("review")
	val review: String

)
package com.example.ilt3_demo.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RestaurantItem(

	@field:SerializedName("pictureId")
	val pictureId: String?,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String
)
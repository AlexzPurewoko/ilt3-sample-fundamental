package com.example.ilt3_demo.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class RestaurantData(

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("restaurants")
	val restaurants: List<RestaurantItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
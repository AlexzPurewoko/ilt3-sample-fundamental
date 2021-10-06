package com.example.ilt3_demo.api

import com.example.ilt3_demo.data.RestaurantData
import com.example.ilt3_demo.data.detail.RestaurantDetail
import retrofit2.http.*

interface RestaurantApi {

    @GET("list")
    suspend fun getAllRestaurant(): RestaurantData

    @GET("detail/{id}")
    suspend fun getDetailRestaurant(@Path("id") restaurantId: String): RestaurantDetail

    @FormUrlEncoded
    @POST("review")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded",
        "X-Auth-Token: 12345"
    )
    suspend fun addRestaurantReview(
        @Field("id") restaurantId: String,
        @Field("name") reviewerName: String,
        @Field("review") reviewContent: String
    )

}
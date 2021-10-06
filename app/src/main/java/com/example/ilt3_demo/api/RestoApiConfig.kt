package com.example.ilt3_demo.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestoApiConfig {
    fun getRestaurantApi(): RestaurantApi {
        return Retrofit.Builder()
            .baseUrl("https://restaurant-api.dicoding.dev")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestaurantApi::class.java)
    }
}
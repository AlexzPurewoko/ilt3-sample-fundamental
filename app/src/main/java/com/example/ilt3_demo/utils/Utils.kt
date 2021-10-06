package com.example.ilt3_demo.utils

val String?.restoPictureUrlFromId : String?
    get() = this?.let {
        "https://restaurant-api.dicoding.dev/images/small/$it"
    }
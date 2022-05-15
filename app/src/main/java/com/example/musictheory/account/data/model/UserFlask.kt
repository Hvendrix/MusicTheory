package com.example.musictheory.account.data.model

import com.google.gson.annotations.SerializedName

data class UserFlask(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
//    @SerializedName("logged_in_as")
//    val login: String,
    @SerializedName("email")
    val login: String,
    @SerializedName("role")
    val role: String
)
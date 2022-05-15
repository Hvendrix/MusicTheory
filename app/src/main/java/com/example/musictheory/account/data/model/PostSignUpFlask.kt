package com.example.musictheory.account.data.model

import com.google.gson.annotations.SerializedName

data class PostSignUpFlask(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("role")
    val role: String,
)

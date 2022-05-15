package com.example.musictheory.account.data.model


import com.google.gson.annotations.SerializedName

data class PostLoginFlask(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)

package com.example.musictheory.account.data.model

import com.google.gson.annotations.SerializedName

data class ResponseToken(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("result")
    val result: String
    )
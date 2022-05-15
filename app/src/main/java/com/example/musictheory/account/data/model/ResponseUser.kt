package com.example.musictheory.account.data.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("data")
    val data : UserFlask,
    @SerializedName("result")
    val result: String
)
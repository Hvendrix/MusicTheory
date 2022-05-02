package com.example.musictheory.home.presentation.model

import com.google.gson.annotations.SerializedName

data class SectionsCollection(
    @SerializedName("data")
    val data: Data,
    @SerializedName("result")
    val result: String
)

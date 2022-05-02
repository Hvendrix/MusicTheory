package com.example.musictheory.home.presentation.model

import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("_id")
    val id: Id,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("name")
    val name: String
)

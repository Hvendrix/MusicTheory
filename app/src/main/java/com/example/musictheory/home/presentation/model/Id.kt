package com.example.musictheory.home.presentation.model

import com.google.gson.annotations.SerializedName

data class Id(
    @SerializedName("\$oid")
    val oid: String
)

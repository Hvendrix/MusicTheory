package com.example.musictheory.home.presentation.model

import com.google.gson.annotations.SerializedName

/**
 * @author Владислав Хвесюк  17.11.2021
 */
data class PostSection(
    @SerializedName("collection")
    val data: List<Collection>,
    @SerializedName("collection_name")
    val collectionName: String
)

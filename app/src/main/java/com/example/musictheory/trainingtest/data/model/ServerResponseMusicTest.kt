package com.example.musictheory.trainingtest.data.model

import com.google.gson.annotations.SerializedName

/**
 * @author Владислав Хвесюк 02.11.2021
 */
data class ServerResponseMusicTest(
    @SerializedName("data")
    val data: List<MusicTest>
)

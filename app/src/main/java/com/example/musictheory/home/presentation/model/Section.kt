package com.example.musictheory.home.presentation.model

import com.google.gson.annotations.SerializedName

/**
 * @author Владислав Хвесюк 02.11.2021
 *
 * Для получения данных пока используем класс ServerResponse
 *
 * @see com.example.musictheory.core.data.model.ServerResponse
 */

data class Section(
    @SerializedName("name")
    val name: String,
    @SerializedName("img_src")
    val imgSrc: String
)

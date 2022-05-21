package com.example.musictheory.core.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Section(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "img_src")
    val imgSrc: String
)

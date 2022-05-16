package com.example.musictheory.trainingtest.data.model

import com.example.musictheory.home.presentation.model.Id
import com.google.gson.annotations.SerializedName

/**
 * @author Владислав Хвесюк 02.11.2021
 */
data class MusicTest(
    @SerializedName("_id")
    val id: Id,
    @SerializedName("name")
    val testName: String,
    @SerializedName("question_array")
    val questionArray: List<Question>,
    @SerializedName("section")
    val sectionsId: List<String>,
    @SerializedName("teacher_id")
    val teacherId: String,
    @SerializedName("test_id")
    val test_id: String


)

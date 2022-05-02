package com.example.musictheory.trainingtest.data.model

import com.example.musictheory.home.presentation.model.Id
import com.google.gson.annotations.SerializedName

/**
 * @author Владислав Хвесюк 02.11.2021
 */
data class MusicTest(
    @SerializedName("_id")
    val id: Id,
    @SerializedName("sections_id")
    val sectionsId: String,
    @SerializedName("question_array")
    val questionArray: List<String>,
    @SerializedName("answer_array")
    val answerArray: List<List<String>>,
    @SerializedName("dislpayed_elemets")
    val displayedElements: List<String>,
    @SerializedName("test_name")
    val testName: String
)

package com.example.musictheory.account.data.model

import com.example.musictheory.trainingtest.data.model.DisplayedElement
import com.google.gson.annotations.SerializedName

data class MusicTestWithoutId(
    @SerializedName("sections_id")
    val sectionsId: String,
    @SerializedName("question_array")
    val questionArray: List<String>,
    @SerializedName("answer_array")
    val answerArray: List<List<String>>,
    @SerializedName("ui_type")
    val uiType: List<String>,
    @SerializedName("displayed_elements")
    val displayedElements: List<List<String>>,
    @SerializedName("test_name")
    val testName: String
)

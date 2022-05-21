package com.example.musictheory.trainingtest.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Question (
    @SerializedName("answer_array")
    var answerArray: MutableList<String> = mutableListOf(),
    @SerializedName("attachment_url")
    val attachmentUrl: String = "",
    @SerializedName("displayed_elements")
    val displayedElements: List<Map<String, String>> = listOf(),
    @SerializedName("generation_seed")
    var generationSeed: MutableMap<Any, Any> = mutableMapOf(),
    @SerializedName("question_text")
    var questionText: String = "",
    @SerializedName("ui_type")
    var uiType: String ="",
)
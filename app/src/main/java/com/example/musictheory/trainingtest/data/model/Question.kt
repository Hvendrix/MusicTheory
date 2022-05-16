package com.example.musictheory.trainingtest.data.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Question (
    @SerializedName("answer_array")
    val answerArray: List<String>,
    @SerializedName("attachment_url")
    val attachmentUrl: String,
    @SerializedName("displayed_elements")
    val displayedElements: List<Map<String, String>>,
    @SerializedName("generation_seed")
    val generationSeed: Map<Any, Any>,
    @SerializedName("question_text")
    val questionText: String,
    @SerializedName("ui_type")
    val uiType: String,
)
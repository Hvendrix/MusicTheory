package com.example.musictheory.account.data.model

import com.example.musictheory.trainingtest.data.model.Question
import com.google.gson.annotations.SerializedName

data class PostEditMusicTest (
    @SerializedName("test_id")
    val testId: String,
    @SerializedName("name")
    val testName: String,
    @SerializedName("section")
    val sectionsId: List<String>,
    @SerializedName("question_array")
    val questionArray: List<Question>,
    @SerializedName("teacher_id")
    val teacherId: String
)
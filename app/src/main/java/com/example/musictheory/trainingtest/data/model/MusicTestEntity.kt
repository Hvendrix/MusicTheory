package com.example.musictheory.trainingtest.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tests")
data class MusicTestEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    val testName: String,
    @ColumnInfo(name = "sections")
    var sectionsId: List<String>,
    @ColumnInfo(name = "question_array")
    var questionArray: List<Question>,
    @SerializedName("teacher_id")
    val teacherId: String,
    @SerializedName("test_id")
    val test_id: String
)



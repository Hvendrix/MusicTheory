package com.example.musictheory.trainingtest.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class MusicTestEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "sections_id")
    var sectionsId: String,
    @ColumnInfo(name = "question_array")
    var questionArray: List<String>,
    @ColumnInfo(name = "answer_array")
    var answerArray: List<List<String>>,
    @ColumnInfo(name = "ui_type")
    var uiType: List<String>,
    @ColumnInfo(name = "displayed_elements")
    var displayedElements: List<List<String>>
)



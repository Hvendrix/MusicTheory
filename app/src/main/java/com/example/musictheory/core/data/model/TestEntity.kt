package com.example.musictheory.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tests")
data class Test(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "id_category")
    val idCategory: Int,
    @ColumnInfo(name = "question_array ")
    val questions: List<String>,
    @ColumnInfo(name = "answer_array ")
    val answers: List<String>,
    @ColumnInfo(name = "question_type ")
    val typeOfQuestion: TypeQuestion
)

enum class TypeQuestion {
    SIMPLE, HARD
}

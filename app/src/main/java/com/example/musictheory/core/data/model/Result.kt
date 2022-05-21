package com.example.musictheory.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "results",
    foreignKeys = [
        ForeignKey(
            entity = Test::class,
            parentColumns = ["id"],
            childColumns = ["id_test"],
            onDelete = CASCADE
        )
    ]
)
// data class Result(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    val id: Long = 1,
//    @ColumnInfo(name = "id_test")
//    val idTest: String,
//    @ColumnInfo(name = "mistake_count")
//    val mistakeCount: Int,
//    @ColumnInfo(name = "mistake_array")
//    val mistakeArray: List<Mistake>
// )
data class Result(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 1,
    @ColumnInfo(name = "id_test")
    val idTest: String,
    @ColumnInfo(name = "mistake_count")
    val mistakeCount: Int,
    @ColumnInfo(name = "mistake_array")
    val mistakeArray: List<List<String>>
)

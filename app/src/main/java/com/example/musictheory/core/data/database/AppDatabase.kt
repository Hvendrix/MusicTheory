package com.example.musictheory.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musictheory.core.data.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTestEntity
import com.example.musictheory.trainingtest.domain.converters.QuestionsConverter

@Database(
    entities = [Result::class, MusicTestEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(QuestionsConverter::class, ResultConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resultsDao(): ResultsDao
}

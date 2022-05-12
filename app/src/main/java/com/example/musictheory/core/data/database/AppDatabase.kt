package com.example.musictheory.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.musictheory.model.Result
import com.example.musictheory.trainingtest.data.model.MusicTestEntity

@Database(
    entities = [Result::class, MusicTestEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ResultConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun resultsDao(): ResultsDao
}

package com.example.musictheory.trainingtest.domain.converters

import androidx.room.TypeConverter
import com.example.musictheory.trainingtest.data.model.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class QuestionsConverter {
    private val gson = Gson()

    @TypeConverter
    fun toJson(segments: List<Question?>?): String? {
        return gson.toJson(segments)
    }

    @TypeConverter
    fun formJson(json: String?): List<Question?>? {
        return gson.fromJson<List<Question?>>(json,
            object : TypeToken<List<Question?>?>() {}.type)
    }

}
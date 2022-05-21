package com.example.musictheory.core.data.database

import androidx.room.TypeConverter
import com.example.musictheory.core.data.model.Mistake
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ResultConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(str: String): List<String> {
        return str.split(",")
    }

    @TypeConverter
    fun toMistakeFromList(list: List<Mistake>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromMistakesToList(str: String): List<Mistake> {
        val listType = object : TypeToken<List<Mistake>>() {}.type
        return gson.fromJson(str, listType)
    }

    @TypeConverter
    fun toStringFromList(list: List<List<String>>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToList(str: String): List<List<String>> {
        val listType = object : TypeToken<List<List<String>>>() {}.type
        return gson.fromJson(str, listType)
    }
}

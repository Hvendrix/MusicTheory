package com.example.musictheory.utils

import timber.log.Timber
import java.lang.Exception

object GeneratedSeedUtils {
    fun returnCount(count: String?): Int{
        return try {
            count?.toFloat()?.toInt() ?: 0
        } catch (e: Exception){
            0
        }
    }

    fun returnNotes(map: Map<Any, Any>): String{
        return try {
            return if(map.get("notes") is String){
                map.get("notes").toString()
            } else ""

        } catch (e: Exception){
            Timber.i("return notes $e")
            ""
        }
    }
}
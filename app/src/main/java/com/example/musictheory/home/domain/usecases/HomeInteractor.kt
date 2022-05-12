package com.example.musictheory.home.domain.usecases

import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.core.domain.usecases.MainInteractor
import com.example.musictheory.home.presentation.model.Id
import com.example.musictheory.trainingtest.data.model.DisplayedElement
import com.example.musictheory.trainingtest.data.model.MusicTest
import com.example.musictheory.trainingtest.data.model.ServerDataMusicTest
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeInteractor(
    private val mainRepository: MainRepository
): MainInteractor{

    suspend fun getCategories(): ServerResponseMusicTest = withContext(Dispatchers.IO){
        return@withContext mainRepository.getCategories()
            .execute().body() ?: error("not found")
    }

    suspend fun getCategoriesLocal(): ServerResponseMusicTest = withContext(Dispatchers.IO){
        return@withContext ServerResponseMusicTest(ServerDataMusicTest("tests", listOf(MusicTest(
            Id("100"),
            "100",
            listOf("какая нота"),
            listOf(listOf("фа", "до", "соль")),
            listOf("stave"),
            listOf(listOf("фа")),
            "знаки"
        ))))
    }

}
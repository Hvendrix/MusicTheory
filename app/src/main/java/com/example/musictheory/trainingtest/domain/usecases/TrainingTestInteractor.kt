package com.example.musictheory.trainingtest.domain.usecases

import com.example.musictheory.account.data.model.MusicTestWithoutId
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.core.domain.usecases.MainInteractor
import com.example.musictheory.home.presentation.model.Id
import com.example.musictheory.trainingtest.data.model.DisplayedElement
import com.example.musictheory.trainingtest.data.model.MusicTest
import com.example.musictheory.trainingtest.data.model.ServerDataMusicTest
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Владислав Хвесюк  08.11.2021
 */
class TrainingTestInteractor(
    private val mainRepository: MainRepository
) : MainInteractor {
    suspend fun getTests(token: String): ServerResponseMusicTest = withContext(Dispatchers.IO) {
        return@withContext mainRepository.getMusicTest(token)
            .execute().body() ?: error("not found")
    }
//    suspend fun getLocalTests(): ServerResponseMusicTest =
//        withContext(Dispatchers.IO){
//            return@withContext ServerResponseMusicTest(ServerDataMusicTest("tests", listOf(MusicTest(
//                Id("1"),
//                "1",
//                listOf("какая нота"),
//                listOf(listOf("фа", "до", "соль")),
//                listOf("stave"),
//                listOf(listOf("фа")),
//                "знаки"
//            ))))
//    }
//
//    suspend fun getLocalTests2(): ServerResponseMusicTest =
//        withContext(Dispatchers.IO){
//            return@withContext ServerResponseMusicTest(ServerDataMusicTest("tests", listOf(MusicTest(
//                Id("1"),
//                "1",
//                listOf("какая это нота"),
//                listOf(listOf("ми", "фа", "соль", "ля", "си", "до2", "ре2")),
//                listOf("stave random pick"),
//                listOf(listOf()),
//                "знаки"
//            ))))
//        }

//    suspend fun postTest(
//        questionArray: List<String>,
//        answerArray: List<List<String>>,
//        uiType: List<String>,
//        testName: String,
//        displayedElement: List<List<String>>,
//    ) = withContext(Dispatchers.IO) {
//        mainRepository.postTest(
//            PostMusicTest(
//                "tests",
//                listOf(
//                    MusicTestWithoutId(
//                        sectionsId = "1",
//                        questionArray = questionArray,
//                        answerArray = answerArray,
//                        uiType = uiType,
//                        testName = testName,
//                        displayedElements = displayedElement,
//                    )
//                )
//            )
//        )
//            .execute()
//    }

//    suspend fun postResult(
//
//    )

//    suspend fun postTest() = withContext(Dispatchers.IO) {
//        mainRepository.postTest(
//            PostMusicTest(
//                "tests",
//                listOf(
//                    MusicTest(
//                        Id("1"),
//                        "1",
//                        listOf("Какие знаки в ля мажоре", "Сколько знаков в ля мажоре"),
//                        listOf(
//                            listOf("диезы", "бемоли"),
//                            listOf("3", "0", "1", "2", "4", "5", "6", "7")
//                        ),
//                        listOf("none", "stave")
//                    )
//                )
//            )
//        )
//            .execute()
//    }
//
//    suspend fun postCollection() = withContext(Dispatchers.IO) {
//        mainRepository.postSection(
//            PostSection(
//                listOf(
//                    Collection(
//                        Id("1"),
//                        "",
//                        "Тест на тональности"
//                    )
//                ),
//                "sections"
//            )
//        )
//            .execute()
//    }
}

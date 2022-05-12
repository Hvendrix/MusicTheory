package com.example.musictheory.account.domain.usecases

import com.example.musictheory.account.data.model.MusicTestWithoutId
import com.example.musictheory.account.data.model.PostDeleteTest
import com.example.musictheory.account.data.model.PostLogin
import com.example.musictheory.account.data.model.PostMusicTest
import com.example.musictheory.account.data.model.PostSignUp
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.core.domain.usecases.MainInteractor
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountInteractor(

    private val mainRepository: MainRepository
) : MainInteractor {
    suspend fun getTests(): ServerResponseMusicTest = withContext(Dispatchers.IO) {
        return@withContext mainRepository.getMusicTest("tests")
            .execute().body() ?: error("not found")
    }

    suspend fun postSignUp(
        token: String,
        name: String,
        teacher: Boolean,
        pass: String
    ) = withContext(
        Dispatchers.IO
    ) {
        var role = "student"
        if (teacher) {
            role = "teacher"
        }
        mainRepository.postSignUp(
            PostSignUp(
                token,
                role,
                name,
                "",
                pass
            )
        )
            .execute()
    }

    suspend fun postLogin(token: String, pass: String) = withContext(Dispatchers.IO) {
        mainRepository.postLogin(
            PostLogin(
                token,
                pass
            )
        )
            .execute()
    }
    suspend fun postTest() = withContext(Dispatchers.IO) {
        mainRepository.postTest(
            PostMusicTest(
                "tests",
                listOf(
                    MusicTestWithoutId(
//                        Id("61938bd1acbd9e7bba8a53d9"),
                        sectionsId = "2",
                        questionArray = listOf("question1"),
                        answerArray = listOf(
                            listOf("yes", "no")
                        ),
                        uiType = listOf("none"),
                        displayedElements = listOf(),
                        testName = "Name"
                    )
                )
            )
        )
            .execute()
    }

    suspend fun postTest(
        questionArray: List<String>,
        answerArray: List<List<String>>,
        uiType: List<String>,
        testName: String,
        displayedElement: List<List<String>> = listOf(),
    ) = withContext(Dispatchers.IO) {
        mainRepository.postTest(
            PostMusicTest(
                "tests",
                listOf(
                    MusicTestWithoutId(
                        sectionsId = "1",
                        questionArray = questionArray,
                        answerArray = answerArray,
                        uiType = uiType,
                        displayedElements = displayedElement,
                        testName = testName
                    )
                )
            )
        )
            .execute()
    }

    suspend fun postDeleteTest() = withContext(Dispatchers.IO) {
        mainRepository.postDeleteTest(
            PostDeleteTest(
                "tests",
                "all",
                listOf()
            )
        )
            .execute()
    }
}

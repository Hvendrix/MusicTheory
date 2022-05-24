package com.example.musictheory.account.domain.usecases

import com.example.musictheory.account.data.model.*
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.core.domain.usecases.MainInteractor
import com.example.musictheory.trainingtest.data.model.Question
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountInteractor(

    private val mainRepository: MainRepository,
) : MainInteractor {
//    suspend fun getTests(): ServerResponseMusicTest = withContext(Dispatchers.IO) {
//        return@withContext mainRepository.getMusicTest("tests")
//            .execute().body() ?: error("not found")
//    }

    suspend fun getTests(token: String): ServerResponseMusicTest = withContext(Dispatchers.IO) {
        return@withContext mainRepository.getMusicTest(token)
            .execute().body() ?: error("not found")
    }

    suspend fun postSignUp(
        token: String,
        name: String,
        teacher: Boolean,
        pass: String,
    ) = withContext(
        Dispatchers.IO
    ) {
        var role = "student"
        if (teacher) {
            role = "teacher_v"
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


    suspend fun postSignUpFlask(
        email: String,
        teacher: Boolean,
        password: String,
        firstName: String = "first_name",
        lastName: String = "last_name",
    ) = withContext(
        Dispatchers.IO
    ) {
        var role = "student"
        if (teacher) {
            role = "teacher_v"
        }
        mainRepository.postSignUpFlask(
            PostSignUpFlask(
                email,
                password,
                firstName,
                lastName,
                role
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

    suspend fun postLoginFlask(email: String, password: String) = withContext(Dispatchers.IO) {
        mainRepository.postLoginFlask(
            PostLoginFlask(
                email,
                password
            )
        )
            .execute()
    }


    suspend fun getUserFlask(token: String) = withContext(Dispatchers.IO) {
        mainRepository.getUserFlask(token)
            .execute()
    }
//    suspend fun postTest() = withContext(Dispatchers.IO) {
//        mainRepository.postTest(
//            PostMusicTest(
//                "tests",
//                listOf(
//                    MusicTestWithoutId(
////                        Id("61938bd1acbd9e7bba8a53d9"),
//                        sectionsId = "2",
//                        questionArray = listOf("question1"),
//                        answerArray = listOf(
//                            listOf("yes", "no")
//                        ),
//                        uiType = listOf("none"),
//                        displayedElements = listOf(),
//                        testName = "Name"
//                    )
//                )
//            )
//        )
//            .execute()
//    }

    suspend fun postTest(
        token: String,
        testName: String,
        sectionId: List<String>,
        questionArray: List<Question>,
        teacherId: String,
    ) = withContext(Dispatchers.IO) {
        mainRepository.postTest(
            token,
            MusicTestWithoutId(
                testName = testName,
                sectionsId = listOf("1"),
                questionArray = questionArray,
                teacherId = teacherId
            )
        )
            .execute()
    }
    suspend fun postTest(
        token: String,
        testName: String,
        sectionId: List<String>,
        questionArray: List<Question>,
        teacherId: String,
        id: Int,
    ) = withContext(Dispatchers.IO) {
        mainRepository.postTest(
            token,
            PostEditMusicTest(
                testId = id.toString(),
                testName = testName,
                sectionsId = listOf("1"),
                questionArray = questionArray,
                teacherId = teacherId
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

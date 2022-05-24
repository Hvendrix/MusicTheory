package com.example.musictheory.core.data.repositories

import com.example.musictheory.account.data.model.*
import com.example.musictheory.core.domain.api.ApiHelper
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.home.presentation.model.PostSection
import com.example.musictheory.trainingtest.data.model.AnswerResult
import com.example.musictheory.trainingtest.data.model.PostResult
import retrofit2.Call

/**
 * @author Владислав Хвесюк 02.11.2021
 *
 * Пока работаем через данный класс
 */
class MainRepositoryImpl(
    private val apiHelper: ApiHelper
) : MainRepository {
    override suspend fun getCollectionByName(
        collectionName: String
    ) = apiHelper.getCollectionByName(collectionName)

    override suspend fun getMusicTest(
        token: String
    ) = apiHelper.getMusicTest(token)

    override suspend fun getCategories(token: String, userId: String)
     = apiHelper.getCategories(token, userId)

    override suspend fun postSection(
        serverData: PostSection
    ) = apiHelper.postSection(serverData)

    override suspend fun postTest(
        token: String,
        postMusicTest: MusicTestWithoutId
    ): Call<AnswerResult> = apiHelper.postTest(token, postMusicTest)

    override suspend fun postTest(
        token: String,
        postMusicTest: PostEditMusicTest
    ): Call<AnswerResult> = apiHelper.postTest(token, postMusicTest)

    override suspend fun postResult(
        postResult: PostResult
    ): Call<PostResult> = apiHelper.postResult(postResult)

    override suspend fun postDeleteTest(
        postDeleteTest: PostDeleteTest
    ): Call<PostDeleteTest> = apiHelper.postDeleteTest(postDeleteTest)

    override suspend fun postSignUp(
        postSignUp: PostSignUp
    ): Call<ResponseLogin> = apiHelper.postSignUp(postSignUp)

    override suspend fun postLogin(
        postLogin: PostLogin
    ): Call<ResponseLogin> = apiHelper.postLogin(postLogin)



    override suspend fun postSignUpFlask(
        postSignUpFlask: PostSignUpFlask
    ): Call<ResponseToken> = apiHelper.postSignUpFlask(postSignUpFlask)

    override suspend fun postLoginFlask(postLoginFlask: PostLoginFlask): Call<ResponseToken> = apiHelper.postLoginFlask(postLoginFlask)
    override suspend fun getUserFlask(token: String): Call<ResponseUser> = apiHelper.getUserFlask(token)

}

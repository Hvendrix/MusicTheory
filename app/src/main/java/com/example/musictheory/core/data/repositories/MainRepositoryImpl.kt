package com.example.musictheory.core.data.repositories

import com.example.musictheory.account.data.model.PostDeleteTest
import com.example.musictheory.account.data.model.PostLogin
import com.example.musictheory.account.data.model.PostMusicTest
import com.example.musictheory.account.data.model.PostSignUp
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.core.domain.api.ApiHelper
import com.example.musictheory.core.domain.repository.MainRepository
import com.example.musictheory.home.presentation.model.PostSection
import com.example.musictheory.trainingtest.data.model.PostResult
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
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
        collectionName: String
    ) = apiHelper.getMusicTest(collectionName)

    override suspend fun getCategories()
     = apiHelper.getCategories()

    override suspend fun postSection(
        serverData: PostSection
    ) = apiHelper.postSection(serverData)

    override suspend fun postTest(
        postMusicTest: PostMusicTest
    ): Call<PostMusicTest> = apiHelper.postTest(postMusicTest)

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
}

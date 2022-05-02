package com.example.musictheory.core.data.api

import com.example.musictheory.account.data.model.PostDeleteTest
import com.example.musictheory.account.data.model.PostLogin
import com.example.musictheory.account.data.model.PostMusicTest
import com.example.musictheory.account.data.model.PostSignUp
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.core.domain.api.ApiHelper
import com.example.musictheory.home.presentation.model.PostSection
import com.example.musictheory.home.presentation.model.SectionsCollection
import com.example.musictheory.trainingtest.data.model.PostResult
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import retrofit2.Call

/**
 * @author Владислав Хвесюк 02.11.2021
 */

class ApiHelperImpl(private val apiService: MusicEducationApiService) : ApiHelper {
    override suspend fun getCollectionByName(collectionName: String): Call<ServerResponse> {
        return apiService.getCollectionByName("tests")
    }

    override suspend fun getMusicTest(collectionName: String): Call<ServerResponseMusicTest> {
        return apiService.getMusicTest("tests")
    }

    override suspend fun getCategories(): Call<ServerResponseMusicTest> {
        return apiService.getCategories()
    }

    override suspend fun postSection(serverData: PostSection): Call<SectionsCollection> {
        return apiService.postSection(serverData)
    }

    override suspend fun postTest(postMusicTest: PostMusicTest): Call<PostMusicTest> {
        return apiService.postTest(postMusicTest)
    }

    override suspend fun postResult(postResult: PostResult): Call<PostResult> {
        return apiService.postResult(postResult)
    }

    override suspend fun postDeleteTest(postDeleteTest: PostDeleteTest): Call<PostDeleteTest> {
        return apiService.postDeleteTest(postDeleteTest)
    }

    override suspend fun postSignUp(postSignUp: PostSignUp): Call<ResponseLogin> {
        return apiService.postSignUp(postSignUp)
    }

    override suspend fun postLogin(postLogin: PostLogin): Call<ResponseLogin> {
        return apiService.postLogin(postLogin)
    }
}

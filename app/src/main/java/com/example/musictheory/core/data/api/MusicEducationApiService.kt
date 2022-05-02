package com.example.musictheory.core.data.api

import com.example.musictheory.account.data.model.PostDeleteTest
import com.example.musictheory.account.data.model.PostLogin
import com.example.musictheory.account.data.model.PostMusicTest
import com.example.musictheory.account.data.model.PostSignUp
import com.example.musictheory.account.data.model.ResponseLogin
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.home.presentation.model.PostSection
import com.example.musictheory.home.presentation.model.SectionsCollection
import com.example.musictheory.trainingtest.data.model.PostResult
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Владислав Хвесюк 02.11.2021
 */

interface MusicEducationApiService {
    @GET("get_data")
    fun getCollectionByName(
        @Query("collection_name") collectionName: String
    ): Call<ServerResponse>

    @GET("get_data")
    fun getMusicTest(
        @Query("collection_name")
        collectionName: String
    ): Call<ServerResponseMusicTest>

    @GET("get_data")
    fun getCategories(
        @Query("collection_name")
        collectionName: String = "tests"
    ): Call<ServerResponseMusicTest>

    @POST("put_data/")
    fun postSection(@Body serverData: PostSection): Call<SectionsCollection>

    @POST("put_data/")
    fun postTest(@Body postMusicTest: PostMusicTest): Call<PostMusicTest>

    @POST("put_data/")
    fun postResult(@Body postResult: PostResult): Call<PostResult>

    @POST("remove_data/")
    fun postDeleteTest(@Body postDeleteTest: PostDeleteTest): Call<PostDeleteTest>

    @POST("signup/")
    fun postSignUp(@Body postSignUp: PostSignUp): Call<ResponseLogin>

    @POST("login/")
    fun postLogin(@Body postLogin: PostLogin): Call<ResponseLogin>
}

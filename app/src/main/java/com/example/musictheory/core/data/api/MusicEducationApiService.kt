package com.example.musictheory.core.data.api

import com.example.musictheory.account.data.model.*
import com.example.musictheory.core.data.model.ServerResponse
import com.example.musictheory.home.presentation.model.PostSection
import com.example.musictheory.home.presentation.model.SectionsCollection
import com.example.musictheory.trainingtest.data.model.AnswerResult
import com.example.musictheory.trainingtest.data.model.PostResult
import com.example.musictheory.trainingtest.data.model.ServerResponseMusicTest
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Владислав Хвесюк 02.11.2021
 */

interface MusicEducationApiService {
    @GET("get_data")
    fun getCollectionByName(
        @Query("collection_name") collectionName: String
    ): Call<ServerResponse>

//    @GET("get_data")
//    fun getMusicTest(
//        @Query("collection_name")
//        collectionName: String
//    ): Call<ServerResponseMusicTest>
@GET("test")
fun getMusicTest(
    @Header("Authorization") token: String
): Call<ServerResponseMusicTest>

//    @GET("get_data")
//    fun getCategories(
//        @Query("collection_name")
//        collectionName: String = "tests"
//    ): Call<ServerResponseMusicTest>
    @GET("test")
    fun getCategories(
    @Header("Authorization") token: String
    ): Call<ServerResponseMusicTest>

    @POST("put_data/")
    fun postSection(@Body serverData: PostSection): Call<SectionsCollection>

    @POST("test")
    fun postTest(@Header("Authorization") token: String, @Body postMusicTest: MusicTestWithoutId): Call<AnswerResult>

    @POST("put_data/")
    fun postResult(@Body postResult: PostResult): Call<PostResult>

    @POST("remove_data/")
    fun postDeleteTest(@Body postDeleteTest: PostDeleteTest): Call<PostDeleteTest>

    @POST("signup/")
    fun postSignUp(@Body postSignUp: PostSignUp): Call<ResponseLogin>

    @POST("login/")
    fun postLogin(@Body postLogin: PostLogin): Call<ResponseLogin>


    @POST("signup")
    fun postSignUpFlask(@Body postSignUpFlask: PostSignUpFlask): Call<ResponseToken>

    @POST("login")
    fun postLoginFlask(@Body postLoginFlask: PostLoginFlask): Call<ResponseToken>

    @GET("user")
    fun getUserFlask(
        @Header("Authorization") token: String
    ): Call<ResponseUser>
}

package com.learningwithmanos.uniexercise.network

import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(
        @Query("ts") timestamp: Long?,
        @Query("apikey") apiKey: String?,
        @Query("hash") hash: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Call<MarvelCharacterResponse>
}
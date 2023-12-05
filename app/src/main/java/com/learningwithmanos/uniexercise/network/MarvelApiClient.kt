package com.learningwithmanos.uniexercise.network

import com.google.gson.GsonBuilder
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MarvelApiClient {

    private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

    val api by lazy { Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MarvelApi::class.java)
    }

    suspend fun getCharacters(limit: Int, offset: Int): Flow<List<Hero>> {
        val generator = MarvelRequestGenerator.createParams()
        return api.getCharacters(generator.timestamp, generator.apiKey, generator.hash, limit, offset)
    }

}

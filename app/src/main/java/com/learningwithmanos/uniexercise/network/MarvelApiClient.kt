package com.learningwithmanos.uniexercise.network

import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MarvelApiClient {

    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"

    private  val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MarvelApi::class.java)

    fun getCharacters(limit: Int, offset: Int): Call<MarvelCharacterResponse> {
        val generator = MarvelRequestGenerator.createParams()
        return api.getCharacters(generator.timestamp, generator.apiKey, generator.hash, limit, offset)
    }

}

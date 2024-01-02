package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.network.MarvelApi
import com.learningwithmanos.uniexercise.network.MarvelApiClient
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import javax.inject.Inject

private var params = MarvelRequestGenerator.createParams()
private var client: MarvelApi = MarvelApiClient.api
interface MarvelRepo  {
    suspend fun getData(): MarvelCharacterResponse
}

class MarvelRepoImpl @Inject constructor() : MarvelRepo {

    override suspend fun getData(): MarvelCharacterResponse {
        var response: MarvelCharacterResponse = MarvelCharacterResponse(0, "", HeroData(listOf()))
        try {
            response = client.getCharacters(
                params.timestamp,
                params.apiKey,
                params.hash,
                20,
                0
            )
        } catch (_: Exception) {
            response.data = HeroData(listOf())
        }

        return response
    }
}
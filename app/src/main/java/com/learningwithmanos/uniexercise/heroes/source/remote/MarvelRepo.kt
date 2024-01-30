package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.network.MarvelApi
import com.learningwithmanos.uniexercise.network.MarvelApiClient
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private var client: MarvelApi = MarvelApiClient.api
interface MarvelRepo  {
    suspend fun getData(): MarvelCharacterResponse
    suspend fun getParams(): Flow<MarvelRequestGenerator>
}

class MarvelRepoImpl @Inject constructor() : MarvelRepo {

    override suspend fun getData(): MarvelCharacterResponse {
        var response = MarvelCharacterResponse(0, "", HeroData(listOf()))
        try {
            val params = MarvelRequestGenerator.createParams()
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

    override suspend fun getParams(): Flow<MarvelRequestGenerator> {
        return flowOf(MarvelRequestGenerator.createParams())
    }
}
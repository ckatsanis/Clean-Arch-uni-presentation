package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.network.ApiException
import com.learningwithmanos.uniexercise.network.MarvelApi
import com.learningwithmanos.uniexercise.network.MarvelApiClient
import com.learningwithmanos.uniexercise.network.NoInternetException
import com.learningwithmanos.uniexercise.network.UnexpectedException
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private var params = MarvelRequestGenerator.createParams()
private var client: MarvelApi = MarvelApiClient.api
interface MarvelRepo  {
    suspend fun getData(): MarvelCharacterResponse
}

class MarvelRepoImpl @Inject constructor() : MarvelRepo {

    override suspend fun getData(): MarvelCharacterResponse {
        return try {
            client.getCharacters(
                params.timestamp,
                params.apiKey,
                params.hash,
                20,
                0
            )
        } catch (e: Exception) {
            throw mapToDomainException(e) {
                null
            }
        }
    }

    fun mapToDomainException(
        remoteException: Exception,
        httpExceptionsMapper: (HttpException) -> Exception? = { null }
    ): Exception {
        return when (remoteException) {
            is IOException -> NoInternetException()
            is HttpException -> httpExceptionsMapper(remoteException) ?: ApiException(
                remoteException.code().toString()
            )

            else -> UnexpectedException(remoteException)
        }
    }
}
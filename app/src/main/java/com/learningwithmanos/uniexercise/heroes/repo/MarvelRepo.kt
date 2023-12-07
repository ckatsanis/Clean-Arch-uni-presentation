package com.learningwithmanos.uniexercise.heroes.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.network.MarvelApi
import com.learningwithmanos.uniexercise.network.MarvelApiClient
import com.learningwithmanos.uniexercise.utils.MarvelRequestGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.flow.Flow
import retrofit2.*
import javax.inject.Inject

private var params = MarvelRequestGenerator.createParams()
private var client: MarvelApi = MarvelApiClient.api
interface MarvelRepo  {
    suspend fun getData(): MarvelCharacterResponse
}

class MarvelRepoImpl @Inject constructor() : MarvelRepo {

    override suspend fun getData(): MarvelCharacterResponse =   client.getCharacters(params.timestamp,params.apiKey, params.hash, 20, 0)
}

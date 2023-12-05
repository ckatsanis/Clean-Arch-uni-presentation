package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.repo.MarvelRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface that wraps the framework that is used for the Rest calls
 */
interface HeroRemoteSource {

    /**
     * @return retrieves the a list of heroes from a certain endpoint
     */
    suspend fun getHeroes(): Flow<List<Hero>>
}

class HeroRemoteSourceImpl @Inject constructor(
    private val restApi: MarvelRepo
): HeroRemoteSource {

    override suspend fun getHeroes(): Flow<List<Hero>> {
        return restApi.getData()
    }

}


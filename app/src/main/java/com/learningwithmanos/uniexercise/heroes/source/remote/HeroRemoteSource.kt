package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import javax.inject.Inject

/**
 * Interface that wraps the framework that is used for the Rest calls
 */
interface HeroRemoteSource {

    /**
     * @return retrieves the a list of heroes from a certain endpoint
     */
    suspend fun getHeroes()
}

class HeroRemoteSourceImpl @Inject constructor(
    private val restFrameworkWrapper: DummyRestFrameworkWrapper,
): HeroRemoteSource {

    override suspend fun getHeroes() {
        restFrameworkWrapper.getHeroes()
    }

}


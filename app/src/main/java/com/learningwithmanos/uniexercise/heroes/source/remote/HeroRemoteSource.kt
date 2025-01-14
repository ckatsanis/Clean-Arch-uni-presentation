package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.RHero
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.heroes.source.local.Converters
import javax.inject.Inject

/**
 * Interface that wraps the framework that is used for the Rest calls
 */
interface HeroRemoteSource {

    /**
     * @return retrieves the a list of heroes from a certain endpoint
     */
    suspend fun getHeroes(): List<Hero>

}

class HeroRemoteSourceImpl @Inject constructor(
    private val restApi: MarvelRepo
): HeroRemoteSource {
    override suspend fun getHeroes(): List<Hero> {
        val response: MarvelCharacterResponse = restApi.getData()

        val hero: List<Hero> = if (response.code == 200) {
            response.data.results.map {
                it.mapToHero()
            }
        } else {
            listOf()
        }

        return hero
    }

    private fun RHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics.availableComics,
        imageUrl = Converters().thumbnailToString(this.imageUrl)
    )

}


package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.LHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Interface that wraps the local storage data framework that is used
 */
interface HeroLocalSource {

    /**
     * @return true if heroes are stored locally else false
     */

    /**
     * Stores a list of heroes to the local data storage
     * @param heroes list of heroes to be stored
     */
    suspend fun storeHeroes(heroes: List<Hero>)

    /**
     * @return the list of heroes stored at the local storage
     */
    fun getHeroes(): Flow<List<Hero>>

    fun isEmpty(): Flow<Boolean>
}

class HeroLocalSourceImpl @Inject constructor(private val marvelDao : MarvelDao): HeroLocalSource {

    override suspend fun storeHeroes(heroes: List<Hero>) {

        val hero: List<LHero> = heroes.map {
            it.mapToLHero()
        }
            marvelDao.insertCharacters(hero)

    }

    override fun getHeroes(): Flow<List<Hero>> {
        return marvelDao.getAllHeroes().map { list -> list.map { it.mapToHero() } }
    }

    override fun isEmpty(): Flow<Boolean> {
        return marvelDao.isEmpty()
    }

    fun LHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )

    fun Hero.mapToLHero() = LHero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )

}
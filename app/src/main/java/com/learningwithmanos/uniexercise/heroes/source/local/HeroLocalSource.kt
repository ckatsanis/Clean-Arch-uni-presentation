package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.Entity
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.LHero
import com.learningwithmanos.uniexercise.heroes.data.RHero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Interface that wraps the local storage data framework that is used
 */
interface HeroLocalSource {

    /**
     * @return true if heroes are stored locally else false
     */
    fun isHeroDataStored(heroId: Int): Flow<Boolean>

    /**
     * Stores a list of heroes to the local data storage
     * @param heroes list of heroes to be stored
     */
    fun storeHeroes(heroes: List<Hero>)

    /**
     * @return the list of heroes stored at the local storage
     */
    fun getHeroes(): List<Hero>

    fun isEmpty(): Flow<Boolean>
}

class HeroLocalSourceImpl @Inject constructor(private val marvelDao : MarvelDao): HeroLocalSource {
    override fun isHeroDataStored(heroId: Int): Flow<Boolean> {
        var r: Boolean = false

        GlobalScope.launch(Dispatchers.IO) {
            r = marvelDao.isHeroDataStored(heroId)
        }

        return flowOf(false)
    }

    override fun storeHeroes(heroes: List<Hero>) {

        val hero: List<LHero> = heroes.map {
            it.mapToLHero()
        }
        GlobalScope.launch(Dispatchers.IO) {
            marvelDao.insertCharacters(hero)
        }

    }

    override fun getHeroes(): List<Hero> {
        var hero: List<Hero> = emptyList()
        GlobalScope.launch(Dispatchers.IO) {
            hero = marvelDao.getAllHeroes().map { it.mapToHero() }
        }
        return hero
    }

    override fun isEmpty(): Flow<Boolean> {
       var r: Boolean = true
        GlobalScope.launch(Dispatchers.IO) {
            r = marvelDao.isEmpty()
        }
        return flowOf(true)
    }

    fun RHero.mapToRHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )

    fun LHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = Converters().intToComics(this.availableComics),
        imageUrl = Converters().stringToThumbnail(this.imageUrl)
    )

    fun Hero.mapToLHero() = LHero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics.availableComics,
        imageUrl = Converters().thumbnailToString(this.imageUrl)
    )

}
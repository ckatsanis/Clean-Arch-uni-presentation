package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.heroes.data.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    fun storeHeroes(heroes: Flow<List<Hero>>)

    /**
     * @return the list of heroes stored at the local storage
     */
    fun getHeroes(): Flow<List<Hero>>

    fun isEmpty(): Flow<Boolean>
}

class HeroLocalSourceImpl @Inject constructor(
    private val dbDao: MarvelDao,
): HeroLocalSource {
    override fun isHeroDataStored(heroId: Int): Flow<Boolean> {
        return flowOf(dbDao.isHeroDataStored(heroId))
    }

    override fun storeHeroes(heroes: Flow<List<Hero>>) {
        dbDao.insertCharacters(hero = heroes)
    }

    override fun getHeroes(): Flow<List<Hero>> {
        return dbDao.getAllHeroes()
    }

    override fun isEmpty(): Flow<Boolean> {
        return flowOf(dbDao.isEmpty())
    }

}
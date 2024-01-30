package com.learningwithmanos.uniexercise.heroes.source.local

import android.util.Log
import com.learningwithmanos.uniexercise.AppPreferences
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

    suspend fun fill(apikey: String, privatekey: String)

    suspend fun setApi(apikey: String, privatekey: String)
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

    override suspend fun fill(apikey: String, privatekey: String) {
        if (apikey != "0cf69d45e2482a87f2a9af138efba603" || privatekey != "8aa649a8b299924f9428f6db08189950b7bfd728") {
            AppPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
            AppPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
            marvelDao.deleteAll()
            Log.d("Dispacher RUN", "fill: local db deleted API: $apikey Private: $privatekey")
        }
    }

    override suspend fun setApi(apikey: String, privatekey: String) {
        if (!(AppPreferences.apikey.equals(apikey)) || !(AppPreferences.privatekey.equals(privatekey))) {
            AppPreferences.apikey = apikey
            AppPreferences.privatekey = privatekey
            marvelDao.deleteAll()
            Log.d("Dispacher RUN", "setApi: local db deleted API: $apikey Private: $privatekey")
        }
    }

    private fun LHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )

    private fun Hero.mapToLHero() = LHero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics,
        imageUrl = this.imageUrl
    )

}
package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.database.MarvelDao
import com.learningwithmanos.uniexercise.heroes.data.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface DBWrapper {
    fun isHeroDataStored(): Flow<Boolean>
    fun storeHeroes(heroes: List<Hero>)
    fun getHeroes(): Flow<List<Hero>>
}

class DummyDBWrapper @Inject constructor() : DBWrapper {
    private lateinit var characterDao: MarvelDao
    override fun isHeroDataStored(): Flow<Boolean> {
        return flowOf(false)
    }

    override fun storeHeroes(heroes: List<Hero>) {
        // do nothing
    }

    override fun getHeroes(): Flow<List<Hero>> {
        return flowOf(characterDao.getAllHeroes())
    }
}
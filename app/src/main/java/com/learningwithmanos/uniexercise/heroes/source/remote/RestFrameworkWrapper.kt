package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.repo.MarvelRepo
import javax.inject.Inject

interface RestFrameworkWrapper{
    suspend fun getHeroes()
}

class DummyRestFrameworkWrapper @Inject constructor(): RestFrameworkWrapper {
    override suspend fun getHeroes() {
        MarvelRepo().GetData()
    }
}
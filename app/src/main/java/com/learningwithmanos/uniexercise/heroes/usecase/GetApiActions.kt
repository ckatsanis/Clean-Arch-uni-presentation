package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import javax.inject.Inject

interface GetApiActions {
    suspend fun fillExecute(apikey: String, privatekey: String)

    suspend fun setApiExecute(apikey: String, privatekey: String)
}

class GetApiActionsImpl @Inject constructor(
    private val heroRepository: HeroRepository
) : GetApiActions {
    override suspend fun fillExecute(apikey: String, privatekey: String) {
        heroRepository.fill(apikey, privatekey)
    }

    override suspend fun setApiExecute(apikey: String, privatekey: String) {
        heroRepository.setApi(apikey, privatekey)
    }

}
package com.learningwithmanos.uniexercise.utils

import com.learningwithmanos.uniexercise.AppPreferences


class MarvelRequestGenerator private constructor(){

    companion object{
        fun createParams(): MarvelRequestGenerator {
            val generator = MarvelRequestGenerator()
            generator.setRequestParams()
            return generator
        }
    }

    val apiKey: String? = AppPreferences.apikey
    val privateKey: String? = AppPreferences.privatekey
    var timestamp :Long? = null
    var hash: String? = null

    private fun setRequestParams(){
        timestamp = System.currentTimeMillis()
        val currentHash: String = timestamp.toString() + privateKey + apiKey
        hash = currentHash.toMD5()
    }

}

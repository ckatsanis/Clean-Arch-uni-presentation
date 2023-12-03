package com.learningwithmanos.uniexercise.utils

class MarvelRequestGenerator private constructor(){

    companion object{
        fun createParams(): MarvelRequestGenerator {
            val generator = MarvelRequestGenerator()
            generator.setRequestParams()
            return generator
        }
    }

    val apiKey: String = "API KEY"
    val privateKey: String = "PrivateKey"
    var timestamp :Long? = null
    var hash: String? = null

    private fun setRequestParams(){
        timestamp = System.currentTimeMillis()
        val currentHash: String = timestamp.toString() + privateKey + apiKey
        hash = currentHash.toMD5()
    }

}
package com.learningwithmanos.uniexercise.utils

class MarvelRequestGenerator private constructor(){

    companion object{
        fun createParams(): MarvelRequestGenerator {
            val generator = MarvelRequestGenerator()
            generator.setRequestParams()
            return generator
        }
    }

    val apiKey: String = "0cf69d45e2482a87f2a9af138efba603"
    val privateKey: String = "8aa649a8b299924f9428f6db08189950b7bfd728"
    var timestamp :Long? = null
    var hash: String? = null

    private fun setRequestParams(){
        timestamp = System.currentTimeMillis()
        val currentHash: String = timestamp.toString() + privateKey + apiKey
        hash = currentHash.toMD5()
    }

}
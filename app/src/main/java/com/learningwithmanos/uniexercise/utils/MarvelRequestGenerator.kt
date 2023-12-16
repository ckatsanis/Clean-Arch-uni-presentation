package com.learningwithmanos.uniexercise.utils

import android.content.SharedPreferences




class MarvelRequestGenerator private constructor(){

    private lateinit var pref: SharedPreferences
    var editor: SharedPreferences.Editor = pref.edit()

    companion object{
        fun createParams(): MarvelRequestGenerator {
            val generator = MarvelRequestGenerator()
            generator.setRequestParams()
            return generator
        }
    }

    val apiKey: String = pref.getString("apikey", null).toString()
    val privateKey: String = pref.getString("privatekey", null).toString()
    var timestamp :Long? = null
    var hash: String? = null

    private fun setRequestParams(){
        timestamp = System.currentTimeMillis()
        val currentHash: String = timestamp.toString() + privateKey + apiKey
        hash = currentHash.toMD5()
    }

}

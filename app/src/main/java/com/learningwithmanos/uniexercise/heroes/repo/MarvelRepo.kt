package com.learningwithmanos.uniexercise.heroes.repo

import android.util.Log
import androidx.activity.ComponentActivity
import com.learningwithmanos.uniexercise.database.MarvelDao
import com.learningwithmanos.uniexercise.database.MarvelDatabase
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.network.MarvelApiClient
import org.w3c.dom.CharacterData
import retrofit2.*

class MarvelRepo ()  {

    suspend fun GetData() {
        // Retrieve data from API using Retrofit and insert into Room Database
        val call: Call<MarvelCharacterResponse> = MarvelApiClient.getCharacters(20,0)
        call.enqueue(object : Callback<MarvelCharacterResponse> {
            override fun onFailure(call: Call<MarvelCharacterResponse>, t: Throwable) {
                t.printStackTrace()
                t.message?.let { Log.e("FAILED API CONNECTION", it) }


            }

            override fun onResponse(
                call: Call<MarvelCharacterResponse>,
                response: Response<MarvelCharacterResponse>
            ) {
                val marvelResponse: MarvelCharacterResponse? = response.body()
                val data: HeroData? = marvelResponse?.data
                data?.results?.let { dao.insertCharacters(it) }
            }

        })

    }

}
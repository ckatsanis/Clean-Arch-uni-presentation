package com.learningwithmanos.uniexercise.heroes.response

import com.google.gson.annotations.SerializedName
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.HeroData
import kotlinx.coroutines.flow.Flow

data class MarvelCharacterResponse(
    @SerializedName("code")
    var code: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("data")
    var data: HeroData
)
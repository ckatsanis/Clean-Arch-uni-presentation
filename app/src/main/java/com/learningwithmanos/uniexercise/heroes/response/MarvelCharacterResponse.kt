package com.learningwithmanos.uniexercise.heroes.response

import com.google.gson.annotations.SerializedName
import com.learningwithmanos.uniexercise.heroes.data.HeroData

data class MarvelCharacterResponse(
    @SerializedName("code")
    var code: Int,

    @SerializedName("status")
    var status: String,

    @SerializedName("data")
    var data: HeroData
)
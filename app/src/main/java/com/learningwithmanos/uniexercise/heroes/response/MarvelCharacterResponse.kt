package com.learningwithmanos.uniexercise.heroes.response

import com.google.gson.annotations.SerializedName
import com.learningwithmanos.uniexercise.heroes.data.HeroData

data class MarvelCharacterResponse(
    @SerializedName("code")
    var code: Int? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("data")
    var data: HeroData
)
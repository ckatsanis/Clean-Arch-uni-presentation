package com.learningwithmanos.uniexercise.heroes.response

import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.google.gson.annotations.SerializedName

class MarvelCharacterResponse(
    @SerializedName("code")
    var code: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("data")
    var data: HeroData
)
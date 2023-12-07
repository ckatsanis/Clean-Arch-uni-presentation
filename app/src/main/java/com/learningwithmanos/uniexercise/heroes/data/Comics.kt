package com.learningwithmanos.uniexercise.heroes.data

import com.google.gson.annotations.SerializedName

data class Comics (
    @SerializedName("available")
    val availableComics: Int
)
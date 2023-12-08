package com.learningwithmanos.uniexercise.heroes.data

import com.google.gson.annotations.SerializedName

data class HeroData (
    @SerializedName("results")
    val results: List<RHero>
)
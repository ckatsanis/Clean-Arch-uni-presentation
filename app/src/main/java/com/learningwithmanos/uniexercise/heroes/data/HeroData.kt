package com.learningwithmanos.uniexercise.heroes.data

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

data class HeroData (
    @SerializedName("results")
    val results: List<Hero>
)
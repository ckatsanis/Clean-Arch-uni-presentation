package com.learningwithmanos.uniexercise.heroes.data

import com.google.gson.annotations.SerializedName

data class RHero(
        @SerializedName("id")
        var id: Int,

        @SerializedName("name")
        var name: String,

        @SerializedName("comics")
        var availableComics: Comics,

        @SerializedName("thumbnail")
        var imageUrl: Thumbnail
)

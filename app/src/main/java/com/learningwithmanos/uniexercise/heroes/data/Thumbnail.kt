package com.learningwithmanos.uniexercise.heroes.data

import com.google.gson.annotations.SerializedName

data class Thumbnail (
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val ext: String
)
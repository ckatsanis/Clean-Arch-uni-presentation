package com.learningwithmanos.uniexercise.utils

import com.learningwithmanos.uniexercise.heroes.data.Thumbnail

fun Thumbnail.getThumbnail() :String {
    val str: String = this.path.replace("http", "https")

    return "${str}.${this.ext}"
}


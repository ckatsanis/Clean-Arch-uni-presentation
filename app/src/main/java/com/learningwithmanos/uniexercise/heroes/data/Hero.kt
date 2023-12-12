package com.learningwithmanos.uniexercise.heroes.data

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Hero(
    var id: Int,
    var name: String,
    var availableComics: Int,
    var imageUrl: String
)

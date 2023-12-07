package com.learningwithmanos.uniexercise.heroes.data

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MarvelTable")
data class Hero(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @ColumnInfo(name = "marvel_name")
    @SerializedName("name")
    var name: String,

    @ColumnInfo(name = "marvel_comics")
    @SerializedName("comics")
    var availableComics: Comics,

    @ColumnInfo(name = "marvel_image")
    @SerializedName("thumbnail")
    var imageUrl: Thumbnail
)
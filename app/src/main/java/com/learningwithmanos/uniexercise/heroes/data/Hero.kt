package com.learningwithmanos.uniexercise.heroes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MarvelTable")
data class Hero(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "marvel_name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "marvel_comics")
    @SerializedName("comics")
    val availableComics: Int,

    @ColumnInfo(name = "marvel_image")
    @SerializedName("image")
    val imageUrl: String
)
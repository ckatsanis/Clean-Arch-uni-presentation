package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.TypeConverter
import com.learningwithmanos.uniexercise.heroes.data.Comics
import com.learningwithmanos.uniexercise.heroes.data.Thumbnail
import com.learningwithmanos.uniexercise.utils.getThumbnail

class Converters {
    @TypeConverter
    fun stringToThumbnail(value: String): Thumbnail {
        val values :List<String> = value.split(".")
        val extension = values.last()
        val url = value.substring(0, value.length - extension.toCharArray().size - 1)
        return Thumbnail(url, extension)
    }

    @TypeConverter
    fun thumbnailToString(thumb: Thumbnail): String {
        return thumb.getThumbnail()
    }

    @TypeConverter
    fun intToComics(value: Int): Comics {
        return Comics(value)
    }

    fun comicsToInt(comics: Comics): Int {
        return comics.availableComics
    }
}
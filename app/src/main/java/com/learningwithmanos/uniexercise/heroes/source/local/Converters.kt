package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.TypeConverter
import com.learningwithmanos.uniexercise.heroes.data.Thumbnail
import com.learningwithmanos.uniexercise.utils.getThumbnail

class Converters {

    @TypeConverter
    fun thumbnailToString(thumb: Thumbnail): String {
        return thumb.getThumbnail()
    }

}
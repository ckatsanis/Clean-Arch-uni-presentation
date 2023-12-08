package com.learningwithmanos.uniexercise.heroes.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.LHero
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Database(entities = [LHero::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MarvelDatabase: RoomDatabase() {
    abstract fun marvelDao(): MarvelDao

}

class MarvelDatabaseImp @Inject constructor(private val dao: MarvelDao) {}
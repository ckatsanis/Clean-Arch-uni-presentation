package com.learningwithmanos.uniexercise.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.learningwithmanos.uniexercise.heroes.data.Hero

@Database(entities = [Hero::class], version = 1, exportSchema = false)
abstract class MarvelDatabase: RoomDatabase() {
    abstract fun marvelDao(): MarvelDao

    companion object {
        @Volatile private var instance: MarvelDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MarvelDatabase::class.java,
            "marvel.db"
        ).build()
    }
}
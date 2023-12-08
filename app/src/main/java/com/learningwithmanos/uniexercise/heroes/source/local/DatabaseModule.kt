package com.learningwithmanos.uniexercise.heroes.source.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDao(marvelDatabase: MarvelDatabase): MarvelDao {
        return marvelDatabase.marvelDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): MarvelDatabase {
        return Room.databaseBuilder(
                appContext.applicationContext,
                MarvelDatabase::class.java,
                "marvel.db"
            )
            .fallbackToDestructiveMigration()
            .build()

    }
}
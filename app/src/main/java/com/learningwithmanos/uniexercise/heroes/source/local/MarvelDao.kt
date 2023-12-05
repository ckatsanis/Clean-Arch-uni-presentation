package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.learningwithmanos.uniexercise.heroes.data.Hero
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
public interface MarvelDao {

    @Insert
    fun insertAll( vararg hero: Hero)

    @Insert
    fun insert(hero: Hero)

    @Insert
    fun insertCharacters(hero: Flow<List<Hero>>)

    @Query("SELECT * FROM MarvelTable")
    fun getAllHeroes(): Flow<List<Hero>>

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun getHeroById(heroId: Int): Hero

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun isHeroDataStored(heroId: Int): Boolean

    @Query("SELECT * FROM MarvelTable")
    fun isEmpty(): Boolean

    @Delete
    fun delete(hero: Hero)
}
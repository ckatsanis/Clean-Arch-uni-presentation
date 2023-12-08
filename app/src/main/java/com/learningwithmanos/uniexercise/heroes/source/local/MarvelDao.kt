package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.LHero
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@Dao
interface MarvelDao {

    @Insert
    fun insertAll( vararg hero: LHero)

    @Insert
    fun insert(hero: LHero)

    @Insert
    fun insertCharacters(hero: List<LHero>)

    @Query("SELECT * FROM MarvelTable")
    fun getAllHeroes(): List<LHero>

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun getHeroById(heroId: Int): LHero

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun isHeroDataStored(heroId: Int): Boolean

    @Query("SELECT (SELECT COUNT(*) FROM MarvelTable) == 0")
    fun isEmpty(): Boolean

    @Delete
    fun delete(hero: LHero)
}
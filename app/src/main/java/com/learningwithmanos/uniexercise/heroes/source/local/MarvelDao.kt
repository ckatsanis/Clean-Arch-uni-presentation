package com.learningwithmanos.uniexercise.heroes.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.learningwithmanos.uniexercise.heroes.data.LHero
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Insert
    suspend fun insertAll( vararg hero: LHero)

    @Insert
    suspend fun insert(hero: LHero)

    @Insert
    suspend fun insertCharacters(hero: List<LHero>)

    @Query("SELECT * FROM MarvelTable")
    fun getAllHeroes(): Flow<List<LHero>>

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun getHeroById(heroId: Int): Flow<LHero>

    @Query("SELECT (SELECT COUNT(*) FROM MarvelTable) == 0")
    fun isEmpty(): Flow<Boolean>

    @Delete
    fun delete(hero: LHero)

    @Query("DELETE FROM MarvelTable")
    suspend fun deleteAll()
}
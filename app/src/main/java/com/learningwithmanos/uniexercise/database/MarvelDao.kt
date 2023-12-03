package com.learningwithmanos.uniexercise.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.learningwithmanos.uniexercise.heroes.data.Hero

@Dao
public interface MarvelDao {

    @Insert
    fun insertAll( vararg hero: Hero)

    @Insert
    fun insert(hero: Hero)

    @Insert
    fun insertCharacters(hero: List<Hero>)

    @Query("SELECT * FROM MarvelTable")
    fun getAllHeroes(): List<Hero>

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun getHeroById(heroId: Int): Hero

    @Delete
    fun delete(hero: Hero)


}
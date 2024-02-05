package com.learningwithmanos.uniexercise.heroes.source.local

import com.learningwithmanos.uniexercise.AppPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroLocalSourceImplTest {

    private lateinit var heroLocalSourceImpl: HeroLocalSourceImpl

    private val marvelDao : MarvelDao = mock()
    private val appPreferences: AppPreferences = mock()


    @Before
    fun setUp() {
        heroLocalSourceImpl = HeroLocalSourceImpl(
            marvelDao
        )
    }

    @Test
    fun `when invoking isHeroDataStored verify results and interactions`() = runTest{
        // when
        heroLocalSourceImpl.isEmpty()

        // then
        verify(marvelDao).isEmpty()
    }

    @Test
    fun `when invoking storeHeroes verify results and interactions`() = runTest{
        // when
        heroLocalSourceImpl.storeHeroes(listOf())

        // then
        verify(marvelDao).insertCharacters(listOf())
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // when
        heroLocalSourceImpl.getHeroes()

        // then
        verify(marvelDao).getAllHeroes()
    }

    @Test
    fun `test Fill`() = runTest {
        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"

        // Act
        (heroLocalSourceImpl.fill("0cf69d45e2482a87f2a9af138efba603", "8aa649a8b299924f9428f6db08189950b7bfd728"))

        verify(appPreferences).apikey = "0cf69d45e2482a87f2a9af138efba603"
        verify(appPreferences).privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
    }

//    @Test
//    fun `test Setapi`() = runTest {
//        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
//        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
//
//        val api = "s"
//        val private = "s"
//
//        // Act
//        heroLocalSourceImpl.setApi(api, private)
//
//        verify(appPreferences).apikey = api
//        verify(appPreferences).privatekey = private
//    }

}
package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.RHero
import com.learningwithmanos.uniexercise.heroes.source.local.Converters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRemoteSourceImplTest {

    private lateinit var heroRemoteSourceImpl: HeroRemoteSourceImpl

    private val restApi: MarvelRepoImpl = mock()

    @Before
    fun setUp() {
        heroRemoteSourceImpl = HeroRemoteSourceImpl(
            restApi
        )
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // when
        heroRemoteSourceImpl.getHeroes()

        // then
        verify(restApi).getData().data.results.map {
            it.mapToHero()
        }
    }

    fun RHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics.availableComics,
        imageUrl = Converters().thumbnailToString(this.imageUrl)
    )

}
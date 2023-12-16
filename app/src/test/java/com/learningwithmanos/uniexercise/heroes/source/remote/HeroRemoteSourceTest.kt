package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.RHero
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import com.learningwithmanos.uniexercise.heroes.source.local.Converters
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRemoteSourceImplTest {

    private lateinit var heroRemoteSourceImpl: HeroRemoteSourceImpl

    private val restApi: MarvelRepo = mock()
    val dummy = listOf(
        Hero(
            id = 1,
            name = "Some",
            availableComics = 10,
            imageUrl = "some.com"
        ),
        Hero(
            id = 2,
            name = "Some",
            availableComics = 10,
            imageUrl = "some.com"
        )
    )

    @Before
    fun setUp() {
        heroRemoteSourceImpl = HeroRemoteSourceImpl(
            restApi
        )
    }

    @Test
    fun `when invoking getHeroes verify results and interactions`() = runTest{
        // given
        given(heroRemoteSourceImpl.getHeroes()).willReturn(dummy)

        // then
        MatcherAssert.assertThat(heroRemoteSourceImpl.getHeroes(), CoreMatchers.equalTo(dummy))
        verify(heroRemoteSourceImpl).getHeroes()
        BDDMockito.verifyNoMoreInteractions(heroRemoteSourceImpl)
    }

    fun RHero.mapToHero() = Hero (
        id = this.id,
        name = this.name,
        availableComics = this.availableComics.availableComics,
        imageUrl = Converters().thumbnailToString(this.imageUrl)
    )

}
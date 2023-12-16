package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetHeroesSortedByHighestNumberOfComicsUCImplTest {

    private lateinit var getHeroesSortedByHighestNumberOfComicsUCImpl: GetHeroesSortedByHighestNumberOfComicsUCImpl

    private val heroRepositoryMock: HeroRepository = mock()

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
        getHeroesSortedByHighestNumberOfComicsUCImpl = GetHeroesSortedByHighestNumberOfComicsUCImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`()= runTest {
        given(heroRepositoryMock.getHeroes()).willReturn(flowOf(dummy))
        val expectedHeroesList = sortHeroListByComicsNumber(dummy)

        // when
        getHeroesSortedByHighestNumberOfComicsUCImpl.execute().collect {actual ->
        // then
        assertThat(actual, equalTo(expectedHeroesList))
        verify(heroRepositoryMock).getHeroes()
        }
    }

    private fun sortHeroListByComicsNumber(heroesList: List<Hero>): List<Hero> {
        return heroesList.sortedByDescending { it.availableComics }
    }

}

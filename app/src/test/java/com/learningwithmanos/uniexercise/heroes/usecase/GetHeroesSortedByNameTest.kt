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
class GetHeroesSortedByNameUCImplTest {

    private lateinit var getHeroesSortedByNameUCImpl: GetHeroesSortedByNameUCImpl

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
        getHeroesSortedByNameUCImpl = GetHeroesSortedByNameUCImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`()= runTest{
        // given
        given(heroRepositoryMock.getHeroes()).willReturn((flowOf(dummy)))
        val expectedHeroesList = sortHeroListByName(dummy)

        // when
        getHeroesSortedByNameUCImpl.execute().collect { actual ->
            // then
            assertThat(actual, equalTo(expectedHeroesList))
            verify(heroRepositoryMock).getHeroes()
        }

    }

    private fun sortHeroListByName(heroesList: List<Hero>): List<Hero> {
        return heroesList.sortedBy { it.name }
    }

}
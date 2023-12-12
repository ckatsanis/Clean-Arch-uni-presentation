package com.learningwithmanos.uniexercise.heroes.usecase

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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

    @Before
    fun setUp() {
        getHeroesSortedByNameUCImpl = GetHeroesSortedByNameUCImpl(
            heroRepositoryMock
        )
    }

    @Test
    fun `when execute is invoked then verify interactions`()= runTest{
        // given
        val heroesList = heroRepositoryMock.getHeroes()
        given(heroRepositoryMock.getHeroes()).willReturn((heroesList))
        val expectedHeroesList = sortHeroListByName(heroesList)

        // when
        getHeroesSortedByNameUCImpl.execute().collect { actual ->
            // then
            assertThat(actual, equalTo(expectedHeroesList))
            verify(heroRepositoryMock).getHeroes()
        }

    }
    private fun sortHeroListByName(heroesList: Flow<List<Hero>>): List<Hero> {
        return heroesList.sortedBy { heroesList.map { list -> list.map{ it.name }  }}
    }

}
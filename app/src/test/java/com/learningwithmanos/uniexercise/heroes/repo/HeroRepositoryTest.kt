package com.learningwithmanos.uniexercise.heroes.repo

import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.RHero
import com.learningwithmanos.uniexercise.heroes.source.local.Converters
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verifyNoMoreInteractions
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.stubbing.OngoingStubbing

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRepositoryImplTest {

    private lateinit var heroRepositoryImpl: HeroRepositoryImpl

    private val heroRemoteSourceMock: HeroRemoteSource = mock()
    private val heroLocalSourceMock: HeroLocalSource = mock()

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
        heroRepositoryImpl = HeroRepositoryImpl(
            heroRemoteSourceMock,
            heroLocalSourceMock
        )
    }

    @Test
    fun `given no data are stored when getHeroes is invoked then verify api call and store to DB`() = runTest{
        // given
        given(heroLocalSourceMock.isEmpty()).willReturn(flowOf(true))
        given(heroRemoteSourceMock.getHeroes()).willReturn(dummy)

        // when
        heroRepositoryImpl.getHeroes().collect { actualHeroes ->
            // then
            assertThat(actualHeroes, equalTo(dummy))
            verify(heroLocalSourceMock).isEmpty()
            verify(heroRemoteSourceMock).getHeroes()
            verify(heroLocalSourceMock).storeHeroes(heroRemoteSourceMock.getHeroes())
            verifyNoMoreInteractions(heroLocalSourceMock)
        }
    }

    @Test
    fun `given data are stored when getHeroes is invoked then verify retrieving of data from DB`() = runTest{
        // given
        given(heroLocalSourceMock.isEmpty()).willReturn(flowOf(false))
        given(heroLocalSourceMock.getHeroes()).willReturn(flowOf(dummy))

        // when
        heroRepositoryImpl.getHeroes().collect { actualHeroes ->
            // then
            assertThat(actualHeroes, equalTo(dummy))
            verify(heroLocalSourceMock).isEmpty()
            verify(heroLocalSourceMock).getHeroes()
            verifyNoMoreInteractions(heroRemoteSourceMock)
        }
    }


}

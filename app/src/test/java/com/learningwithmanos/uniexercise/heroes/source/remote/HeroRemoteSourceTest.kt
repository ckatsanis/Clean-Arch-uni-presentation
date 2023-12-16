package com.learningwithmanos.uniexercise.heroes.source.remote

import com.learningwithmanos.uniexercise.heroes.data.Comics
import com.learningwithmanos.uniexercise.heroes.data.HeroData
import com.learningwithmanos.uniexercise.heroes.data.RHero
import com.learningwithmanos.uniexercise.heroes.data.Thumbnail
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class HeroRemoteSourceImplTest {

    private lateinit var heroRemoteSourceImpl: HeroRemoteSourceImpl

    private val restApi: MarvelRepo = mock()
    val dummy = MarvelCharacterResponse(
            code = 200,
            status = "Some",
            data = HeroData(
                listOf(
                RHero(
                    id = 1,
                    name = "some",
                    availableComics = Comics(1),
                    imageUrl = Thumbnail("www","jpg")
                ),
                RHero(
                    id = 1,
                    name = "some",
                    availableComics = Comics(1),
                    imageUrl = Thumbnail("www","jpg")
                )
            )
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
        given(restApi.getData()).willReturn(dummy)

//        when
        val actual = restApi.getData()

        // then
        MatcherAssert.assertThat(actual, equalTo(dummy))
        verify(restApi).getData()
    }

}
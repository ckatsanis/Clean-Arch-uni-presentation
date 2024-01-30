package com.learningwithmanos.uniexercise.heroes.ui

import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.usecase.GetApiActions
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesSortedByHighestNumberOfComicsUC
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesSortedByNameUC
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesUC
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiViewModelTest {

    private lateinit var apiViewModel: ApiViewModel

    private val getApiActions: GetApiActions = mock()
    private val appPreferences: AppPreferences = mock()

    @Before
    fun setUp() {
        apiViewModel = ApiViewModel(
            getApiActions
        )
    }

    @Test
    fun `when setApi is invoked then verify interactions`()= runTest{
        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"

        // Act
        apiViewModel.setApi("0cf69d45e2482a87f2a9af138efba603", "8aa649a8b299924f9428f6db08189950b7bfd728")

        Mockito.verify(AppPreferences).apikey = "0cf69d45e2482a87f2a9af138efba603"
        Mockito.verify(AppPreferences).privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
    }

    @Test
    fun `when fill is invoked then verify interactions`()= runTest{
        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"

        val api = "s"
        val private = "s"

        // Act
        apiViewModel.fill(api, private)

        Mockito.verify(AppPreferences).apikey = api
        Mockito.verify(AppPreferences).privatekey = private
    }

    @Test
    fun `when apikeyUpdate is invoked then verify interactions`()= runTest{
        val api = "0cf69d45e2482a87f2a9af138efba603"
        // Act
        apiViewModel.updateApiKey(api)

        verify(AppPreferences).apikey = api
    }

    @Test
    fun `when privateKeyUpdate is invoked then verify interactions`()= runTest{
        val private = "8aa649a8b299924f9428f6db08189950b7bfd728"
        // Act
        apiViewModel.updatePrivateKey(private)

        verify(AppPreferences).privatekey = private
    }

}

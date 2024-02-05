package com.learningwithmanos.uniexercise.heroes.usecase
//
//import com.learningwithmanos.uniexercise.AppPreferences
//import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import org.mockito.Mockito
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.verify
//
//class GetApiActionsTest {
//
//    private lateinit var getApiActionsImpl: GetApiActionsImpl
//    private val heroRepositoryMock: HeroRepository = mock()
//    private val appPreferences: AppPreferences = mock()
//
//    @Before
//    fun setUp() {
//        getApiActionsImpl = GetApiActionsImpl(
//            heroRepositoryMock
//        )
//    }
//
//    @Test
//    fun `when fillExecute is invoked then verify interactions`()= runTest{
//        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
//        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
//
//        // Act
//        getApiActionsImpl.setApiExecute("0cf69d45e2482a87f2a9af138efba603", "8aa649a8b299924f9428f6db08189950b7bfd728")
//
//        verify(AppPreferences).apikey = "0cf69d45e2482a87f2a9af138efba603"
//        verify(AppPreferences).privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
//    }
//
//    @Test
//    fun `when setApiExecute is invoked then verify interactions`()= runTest{
//        appPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
//        appPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
//
//        val api = "s"
//        val private = "s"
//
//        // Act
//        getApiActionsImpl.setApiExecute(api, private)
//
//        verify(AppPreferences).apikey = api
//        verify(AppPreferences).privatekey = private
//    }
//
//}

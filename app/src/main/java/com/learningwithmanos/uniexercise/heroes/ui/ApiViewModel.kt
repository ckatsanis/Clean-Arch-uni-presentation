package com.learningwithmanos.uniexercise.heroes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.local.MarvelDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val heroLocalSource: HeroLocalSource,
) : ViewModel() {


   val localSource: HeroLocalSource = heroLocalSource

   private fun delete(): Flow<Thread.State> = flow {
       localSource.delete()
   }

    @OptIn(DelicateCoroutinesApi::class)
    fun fill(apikey: String, privatekey: String) {
        if (apikey != "0cf69d45e2482a87f2a9af138efba603" || privatekey != "8aa649a8b299924f9428f6db08189950b7bfd728") {
            AppPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
            AppPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
            delete()
            Log.d("Dispacher RUN", "fill: local db deleted API: $apikey Private: $privatekey")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun setApi(apikey: String, privatekey: String) {
        if (!(AppPreferences.apikey.equals(apikey)) || !(AppPreferences.privatekey.equals(privatekey))) {
            AppPreferences.apikey = apikey
            AppPreferences.privatekey = privatekey
            delete()
            Log.d("Dispacher RUN", "setApi: local db deleted API: $apikey Private: $privatekey")
        }
    }

}
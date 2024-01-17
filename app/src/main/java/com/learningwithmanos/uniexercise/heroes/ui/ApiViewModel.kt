package com.learningwithmanos.uniexercise.heroes.ui

import android.util.Log
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.local.MarvelDao
import com.learningwithmanos.uniexercise.heroes.usecase.GetApiActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val getApiUC: GetApiActions,
) : ViewModel() {

    private val _privateKeyStateFlow = MutableStateFlow("")
    private val _apiKeyStateFlow = MutableStateFlow("")

    val apiKeyStateFlow: StateFlow<String> = _apiKeyStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _apiKeyStateFlow.value
    )

    val privateKeyStateFlow: StateFlow<String> = _privateKeyStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _privateKeyStateFlow.value
    )

    val isButtonEnabledStateFlow: StateFlow<Boolean> = combine(_privateKeyStateFlow, _apiKeyStateFlow) {
        privateKey, apiKey ->
        (privateKey.isBlank()) || (apiKey.isBlank())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = false
    )

    fun setApi(apikey: String, privatekey: String) {
        viewModelScope.launch {
            getApiUC.setApiExecute(apikey, privatekey)
        }
    }

    fun fill(apikey: String, privatekey: String) {
        viewModelScope.launch {
            getApiUC.fillExecute(apikey, privatekey)
        }
    }

    fun updateApiKey(apiKey: String) {
        _apiKeyStateFlow.update {
            apiKey
        }
    }

    fun updatePrivateKey(privatekey: String) {
        _privateKeyStateFlow.update {
            privatekey
        }
    }

}
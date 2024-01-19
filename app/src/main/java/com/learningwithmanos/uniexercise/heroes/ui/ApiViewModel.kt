package com.learningwithmanos.uniexercise.heroes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.usecase.GetApiActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    private val getApiUC: GetApiActions,
) : ViewModel() {

    private val _privateKeyStateFlow = MutableStateFlow(AppPreferences.privatekey.toString())
    private val _apiKeyStateFlow = MutableStateFlow(AppPreferences.apikey.toString())

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
        (privateKey.isNotBlank()) && (apiKey.isNotBlank())
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
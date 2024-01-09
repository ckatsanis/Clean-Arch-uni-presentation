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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    private val _buttonStateFlow = MutableStateFlow(false)

    val buttonStateFlow: StateFlow<Boolean> = _buttonStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _buttonStateFlow.value
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val buttonCheckStateFlow: Flow<Boolean> = buttonStateFlow.flatMapLatest { buttonState ->
        when (buttonState) {
            false -> validateFields()
            true -> validateFields()
        }
    }

    fun isEnable(): Boolean {
        var result = false
        GlobalScope.async { buttonCheckStateFlow.collect { result = it } }

        return result
    }

    private fun validateFields(): Flow<Boolean> {
        return flowOf(!(AppPreferences.apikey.isNullOrBlank() || AppPreferences.privatekey.isNullOrBlank()))
    }

    val localSource: HeroLocalSource = heroLocalSource

    suspend fun delete() {
       localSource.delete()
    }

}
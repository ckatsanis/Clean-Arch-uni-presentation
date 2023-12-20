package com.learningwithmanos.uniexercise.heroes.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learningwithmanos.uniexercise.heroes.data.Hero
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import com.learningwithmanos.uniexercise.heroes.response.MarvelCharacterResponse
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSource
import com.learningwithmanos.uniexercise.heroes.source.remote.HeroRemoteSourceImpl
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesSortedByHighestNumberOfComicsUC
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesSortedByNameUC
import com.learningwithmanos.uniexercise.heroes.usecase.GetHeroesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val getHeroesUC: GetHeroesUC,
    private val getHeroesSortedByNameUC: GetHeroesSortedByNameUC,
    private val getHeroesSortedByHighestNumberOfComicsUC: GetHeroesSortedByHighestNumberOfComicsUC,
    private val marvelResponse: HeroRemoteSource,
    private val heroLocalSource: HeroLocalSource,
) : ViewModel() {

    private var _selectedTabStateFlow: MutableStateFlow<Tab> = MutableStateFlow(Tab.Heroes)

    val selectedTabStateFlow: StateFlow<Tab> = _selectedTabStateFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _selectedTabStateFlow.value
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val heroesStateFlow: StateFlow<List<HeroTileModel>> = selectedTabStateFlow.flatMapLatest { selectedTab ->
        when (selectedTab) {
            Tab.Heroes -> getHeroesUC.execute().map { list -> list.map { it.mapHeroToHeroTileModel() }}
            Tab.SortedByNameHeroes -> getHeroesSortedByNameUC.execute()
                .map { list -> list.map { it.mapHeroToHeroTileModel() }}

            Tab.SortedByComicHeroes -> getHeroesSortedByHighestNumberOfComicsUC.execute()
                .map { list -> list.map { it.mapHeroToHeroTileModel() }}
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = listOf()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val resaultStatus: StateFlow<StatusModel> = selectedTabStateFlow.flatMapLatest { selectedTab ->
        when(selectedTab) {
            Tab.Heroes -> marvelResponse.getRespond().map { it.ToResponse() }
            Tab.SortedByNameHeroes -> marvelResponse.getRespond().map { it.ToResponse() }
            Tab.SortedByComicHeroes -> marvelResponse.getRespond().map { it.ToResponse() }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = StatusModel(200, "OK")
    )

    val localSource: HeroLocalSource = heroLocalSource

    /**
     * Utilises corresponding UC to retrieve data based on the selectedTab.
     * @param selectedTab
     */
    fun getSelectedIndex(selectedTab: Tab): Int {
        return when (selectedTab) {
            Tab.Heroes -> 0
            Tab.SortedByNameHeroes -> 1
            Tab.SortedByComicHeroes -> 2
        }
    }

    /**
     * Sets the selected tab
     */
    fun selectTab(tab: Tab) {
        _selectedTabStateFlow.value = tab
    }

}

data class HeroTileModel(
    val title: String,
    val imageUrl: String,
)

data class StatusModel(
    val code: Int,
    val status: String,
)

fun Hero.mapHeroToHeroTileModel(): HeroTileModel {
    return HeroTileModel(
        title = "$name, comics - ${availableComics}",
        imageUrl = imageUrl// image view are implemented with coil jc
    )
}

fun MarvelCharacterResponse.ToResponse() : StatusModel {
    Log.d("API RESPONSE", "ToResponse: ${code} and $status")
    return StatusModel(
        code = code,
        status = status
    )
}
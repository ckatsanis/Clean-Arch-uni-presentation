package com.learningwithmanos.uniexercise.heroes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.data.Tab
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(
    onIconButtonPressed: () -> Unit,
    viewModel: HeroesViewModel = hiltViewModel()
) {

    val selectedTab = viewModel.selectedTabStateFlow.collectAsState()
    val heroesList = viewModel.heroesStateFlow.collectAsState()

    Scaffold (
        modifier = Modifier.nestedScroll(TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()).nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Marvel Characters List")
                },
                actions = {
                    IconButton(onClick = onIconButtonPressed ) {
                        Icon(
                            imageVector = Icons.Filled.Build,
                            contentDescription = "Api Setup"
                        )
                    }
                }
            )
        }
    ) {
        innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            TabRow(
                selectedTabIndex = viewModel.getSelectedIndex(selectedTab.value),
            ) {
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.Heroes) },
                    textAlign = TextAlign.Center,
                    text = "Heroes"
                )
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByNameHeroes) },
                    textAlign = TextAlign.Center,
                    text = "A-Z Heroes"
                )
                Text(
                    modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByComicHeroes) },
                    textAlign = TextAlign.Center,
                    text = "Heroes by Comic"
                )
            }

            if ( !AppPreferences.apikey.isNullOrBlank() || !AppPreferences.privatekey.isNullOrBlank()) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (heroesList.value.isNotEmpty())
                            ShowHeroes(heroes = heroesList.value)
                        else
                            ShowError()
                    }
            }
            else
                AlertDialog(
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Api Setup"
                        )
                    },
                    title = {
                        Text(text = "Empty Api Values")
                    },
                    text = {
                        Text(text = "Your Api setting is null. Please fill to continue")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onIconButtonPressed
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    onDismissRequest = {}
                )
        }
    }
}

@Composable
fun ShowHeroes(heroes: List<HeroTileModel>) {
    heroes.forEach {
        Row {
            AsyncImage(
                model = it.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(text = it.title)
        }
    }
}

@Composable
fun ShowError() {
    Row {
        Text(text = "There are problem with request to the api.")
    }
    Row {
        Text(text = "Check if api and private key are correct")
    }
    Row {
        Text(text = "Go to Api configuration page to configure")
    }
}

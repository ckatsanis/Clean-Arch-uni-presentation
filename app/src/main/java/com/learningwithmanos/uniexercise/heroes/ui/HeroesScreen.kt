package com.learningwithmanos.uniexercise.heroes.ui

import android.content.SharedPreferences
import android.widget.ImageView
import android.widget.Toolbar
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.utils.loadImage
import de.hdodenhof.circleimageview.CircleImageView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(
    navController: NavHostController,
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
                    IconButton(onClick = { navController.navigate("Api") }) {
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

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ShowHeroes(heroes = heroesList.value)
            }
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

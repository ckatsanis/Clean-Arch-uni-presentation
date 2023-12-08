package com.learningwithmanos.uniexercise.heroes.ui

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.learningwithmanos.uniexercise.heroes.data.Tab
import com.learningwithmanos.uniexercise.utils.loadImage
import de.hdodenhof.circleimageview.CircleImageView

@Composable
fun HeroesScreen(
    viewModel: HeroesViewModel = hiltViewModel()
) {

    val selectedTab = viewModel.selectedTabStateFlow.collectAsState()
    val heroesList = viewModel.heroesStateFlow.collectAsState()

    Column {
        TabRow(selectedTabIndex = viewModel.getSelectedIndex(selectedTab.value)) {
            Text(modifier = Modifier.clickable { viewModel.selectTab(Tab.Heroes) }, textAlign = TextAlign.Center, text = "Heroes")
            Text(modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByNameHeroes) }, textAlign = TextAlign.Center, text = "A-Z Heroes")
            Text(modifier = Modifier.clickable { viewModel.selectTab(Tab.SortedByComicHeroes) }, textAlign = TextAlign.Center, text = "Heroes by Comic")
        }

        Column {
            ShowHeroes(heroes = heroesList.value)
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


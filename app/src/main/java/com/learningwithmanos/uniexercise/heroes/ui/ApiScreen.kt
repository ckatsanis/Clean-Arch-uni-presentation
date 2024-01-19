package com.learningwithmanos.uniexercise.heroes.ui

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.learningwithmanos.uniexercise.AppPreferences
import com.learningwithmanos.uniexercise.heroes.repo.HeroRepository
import com.learningwithmanos.uniexercise.heroes.source.local.HeroLocalSource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(
    onIconButtonPressed: () -> Unit,
    viewModel: ApiViewModel = hiltViewModel(),
) {
    val apiKey: String = viewModel.apiKeyStateFlow.collectAsState().value
    val privateKey: String = viewModel.privateKeyStateFlow.collectAsState().value

    val isButtonEnabled: Boolean = viewModel.isButtonEnabledStateFlow.collectAsState().value

    Scaffold (
        modifier = Modifier.nestedScroll(
            TopAppBarDefaults.pinnedScrollBehavior(
                rememberTopAppBarState()
            ).nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Api Configuration")
                },
                actions = {
                    IconButton(onClick = onIconButtonPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
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
        ) {
            Text(
                text = "Api Key",
                style = MaterialTheme.typography.bodyLarge
            )


            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = apiKey,
                onValueChange = { viewModel.apiKeyStateFlow.toString() } ,
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Text(
                text = "Private Key",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = privateKey,
                onValueChange = { viewModel.privateKeyStateFlow.value },
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Button(
                onClick = {
                    viewModel.setApi(apiKey, privateKey)
                    onIconButtonPressed.invoke()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isButtonEnabled
            ) {
                Text(text = "Save")
            }

            Button(
                onClick = {
                    viewModel.fill(apiKey, privateKey)
                    onIconButtonPressed.invoke()
                    },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text(text = "If you don't have Keys press here!")
            }

        }

    }
}
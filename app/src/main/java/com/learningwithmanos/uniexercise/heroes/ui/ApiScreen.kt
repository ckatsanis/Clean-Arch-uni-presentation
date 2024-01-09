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
    navController: NavHostController,
    viewModel: ApiViewModel = hiltViewModel(),
) {

    var apikey by rememberSaveable { mutableStateOf(AppPreferences.apikey) }
    var privatekey by rememberSaveable { mutableStateOf(AppPreferences.privatekey) }
    val local = viewModel.localSource

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
                    IconButton(onClick = { navController.navigate("Heroes") }) {
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
                value = apikey.toString(),
                onValueChange = { apikey = it },
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Text(
                text = "Private Key",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = privatekey.toString(),
                onValueChange = { privatekey = it },
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Button(
                onClick = {
                    setApi(apikey.toString(), privatekey.toString(), local)
                    navController.navigate("Heroes")
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = viewModel.isEnable()
            ) {
                Text(text = "Save")
            }

            Button(
                onClick = {
                    fill(apikey.toString(), privatekey.toString(), local)
                    navController.navigate("Heroes")
                    },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text(text = "If you don't have Keys press here!")
            }

        }

    }
}


@OptIn(DelicateCoroutinesApi::class)
fun fill(apikey: String, privatekey: String, localSource: HeroLocalSource) {
    val view = ApiViewModel(localSource)

    if (apikey != "0cf69d45e2482a87f2a9af138efba603" || privatekey != "8aa649a8b299924f9428f6db08189950b7bfd728") {
        AppPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
        AppPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
        GlobalScope.async{ view.delete() }
        Log.d("Dispacher RUN", "fill: local db deleted API: $apikey Private: $privatekey")
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun setApi(apikey: String, privatekey: String, localSource: HeroLocalSource) {
    val view = ApiViewModel(localSource)

    if (!(AppPreferences.apikey.equals(apikey)) || !(AppPreferences.privatekey.equals(privatekey))) {
        AppPreferences.apikey = apikey
        AppPreferences.privatekey = privatekey
        GlobalScope.async{ view.delete() }
        Log.d("Dispacher RUN", "setApi: local db deleted API: $apikey Private: $privatekey")
    }
}
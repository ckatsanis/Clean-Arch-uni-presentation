package com.learningwithmanos.uniexercise.heroes.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.learningwithmanos.uniexercise.AppPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(
    navController: NavHostController,
    viewModel: HeroesViewModel = hiltViewModel()

) {

    var apikey by rememberSaveable { mutableStateOf(AppPreferences.apikey) }
    var privatekey by rememberSaveable { mutableStateOf(AppPreferences.privatekey) }

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

            if ((apikey.isNullOrBlank() || (privatekey.isNullOrBlank())))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                    ) {
                    Text(text = "Save")
                }
            else
                Button(
                    onClick = {
                        setApi(apikey.toString(), privatekey.toString())
                        navController.navigate("Heroes")
                        },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                ) {
                    Text(text = "Save")
                }

            Button(
                onClick = { fill() },
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            ) {
                Text(text = "If you don't have Keys press here!")
            }

        }

    }
}

fun setApi(apikey: String, privatekey: String) {
    AppPreferences.apikey = apikey
    AppPreferences.privatekey = privatekey
}

fun fill() {
    AppPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
    AppPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
}
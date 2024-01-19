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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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
                onValueChange = {
                    viewModel.updateApiKey(it)
                } ,

                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Text(
                text = "Private Key",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = privateKey,
                onValueChange = {
                    viewModel.updatePrivateKey(it)
                },
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
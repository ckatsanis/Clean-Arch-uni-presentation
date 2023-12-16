package com.learningwithmanos.uniexercise.heroes.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.learningwithmanos.uniexercise.AppPreferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiScreen(navController: NavHostController) {

    var apikey: String? = AppPreferences.apikey
    var privatekey: String? = AppPreferences.privatekey

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
                onValueChange = {
                    if (it !== "")
                        apikey = it
                },
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            Text(
                text = "Private Key",
                style = MaterialTheme.typography.bodyLarge
            )

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = privatekey.toString(),
                onValueChange = {
                    if (it !== "")
                        privatekey = it
                },
                placeholder = { Text(text = "e.g. Hexamine") },
            )

            if (!(apikey.equals("") || !(privatekey.equals(""))))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false
                    ) {
                    Text(text = "Save")
                }
            else
                Button(
                    onClick = { checks(apikey.toString(), privatekey.toString()) },
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

fun checks(apikey: String, privatekey: String) {
    AppPreferences.apikey = apikey
    AppPreferences.privatekey = privatekey
}

fun fill() {
    AppPreferences.apikey = "0cf69d45e2482a87f2a9af138efba603"
    AppPreferences.privatekey = "8aa649a8b299924f9428f6db08189950b7bfd728"
}
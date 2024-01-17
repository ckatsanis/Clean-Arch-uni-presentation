package com.learningwithmanos.uniexercise

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.learningwithmanos.uniexercise.heroes.ui.ApiScreen
import com.learningwithmanos.uniexercise.heroes.ui.HeroesScreen
import com.learningwithmanos.uniexercise.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppPreferences.setup(applicationContext)

        setContent {
            MyApplicationTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Heroes"
                ) {

                    composable("Heroes") {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            HeroesScreen(onIconButtonPressed = { navController.navigate("Api") })
                        }
                    }
                    composable("Api") {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            ApiScreen(onIconButtonPressed = { navController.navigate("Heroes")} )
                        }
                    }
                }
            }
        }
    }
}
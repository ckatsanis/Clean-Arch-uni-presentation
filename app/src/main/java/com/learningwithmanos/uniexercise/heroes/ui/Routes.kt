package com.learningwithmanos.uniexercise.heroes.ui

sealed class Routes(val route: String) {
    object Heroes : Routes("HeroScreen")
    object Api : Routes("ApiScreen")
}
package com.example.DLChordsTT.features.lists_music.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.DLChordsTT.features.lists_music.navigation.Destinations.*

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = StoredAudios.route) {
        composable(StoredAudios.route) {
            StoredAudiosScreen()
        }
        composable(Pantalla2.route) {
            Pantalla2()
        }
    }
}
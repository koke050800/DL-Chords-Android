package com.example.DLChordsTT.features.audio_lists.ui.screens

import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.DLChordsTT.features.audio_lists.data.Audio
import com.example.DLChordsTT.features.audio_lists.navigation.Destinations.*

@Composable
fun NavigationHost(
    navController: NavHostController
) {
    var audioListForPreview = mutableListOf<Audio>()

    for (i in 0..12) audioListForPreview.add(
        index = i,
        Audio(
            uri = "".toUri(),
            displayName = "Me iré con ella",
            id = 0L,
            artist = "Santa Fe Klan",
            data = "",
            duration = 267491,
            title = "TITLE"
        ),
    )
    NavHost(navController = navController, startDestination = StoredAudios.route) {
        composable(StoredAudios.route) {
            StoredAudiosScreen(audioListForPreview)
        }
        composable(Pantalla2.route) {
            Pantalla2(audioListForPreview)
        }
    }
}
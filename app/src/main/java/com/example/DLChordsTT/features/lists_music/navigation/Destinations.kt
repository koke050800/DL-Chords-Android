package com.example.DLChordsTT.features.lists_music.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Pantalla1: Destinations("pantalla1", "Pantalla 1", Icons.Filled.MusicNote)
    object Pantalla2: Destinations("pantalla2", "Pantalla 2", Icons.Filled.LibraryMusic)
}

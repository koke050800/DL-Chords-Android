package com.example.DLChordsTT.features.audio_lists.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object StoredAudios: Destinations("storedaudios", "Almacenados", Icons.Filled.MusicNote)
    object ProcessedAudios: Destinations("processedaudios", "Procesados", Icons.Filled.LibraryMusic)
}

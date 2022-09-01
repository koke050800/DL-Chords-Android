package com.example.DLChordsTT.features.audio_list.features.ui.screens

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.rememberNavController
import com.example.DLChordsTT.features.audio_list.features.navigation.Destinations
import com.example.DLChordsTT.features.audio_list.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.audio_list.ui.screens.NavigationHostScreens
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioListActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DLChordsTheme {

                val permissionState = rememberPermissionState(
                    permission = Manifest.permission.READ_EXTERNAL_STORAGE
                )

                if (permissionState.hasPermission) {
                    val audioViewModel: AudioViewModel by viewModels()
                    MainScreen(audioViewModel = audioViewModel)
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "Sin permiso para acceder al almacenamiento")
                    }
                }

            }
        }


    }
}

@Composable
fun MainScreen(audioViewModel: AudioViewModel) {
    val navController = rememberNavController()
    val navigationItems = listOf(
        Destinations.StoredAudios,
        Destinations.ProcessedAudios,
    )
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems) },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        backgroundColor = MaterialTheme.colors.background
    ) {
        NavigationHostScreens(navController, audioViewModel = audioViewModel)
    }
}


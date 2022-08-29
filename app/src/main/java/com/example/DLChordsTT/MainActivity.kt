package com.example.DLChordsTT

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
import com.example.DLChordsTT.features.audio_lists.navigation.Destinations
import com.example.DLChordsTT.features.audio_lists.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.audio_lists.ui.screens.NavigationHost
import com.example.DLChordsTT.features.audio_lists.view_models.AudioViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                    MainScreen(audioViewModel)
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
        Destinations.Pantalla2
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems) },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        backgroundColor = MaterialTheme.colors.background
    ) {
        NavigationHost(navController, audioViewModel = audioViewModel)
    }
}


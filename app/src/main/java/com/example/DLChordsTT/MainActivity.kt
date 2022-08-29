package com.example.DLChordsTT

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.rememberNavController
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.navigation.Destinations
import com.example.DLChordsTT.features.audio_lists.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.audio_lists.ui.screens.NavigationHost
import com.example.DLChordsTT.features.audio_lists.view_models.AudioViewModel
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.viewmodel.AudioProcViewModel
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
                    val storedAudiosList = audioViewModel.storedAudioList

                    val audioprocViewModel: AudioProcViewModel by viewModels()
                    val procAudiosList = audioprocViewModel.processedAudioList

                         /*var storedAudiosList = mutableListOf<Audio>()

                    for (i in 0..12) storedAudiosList.add(
                        index = i,
                        Audio(
                            uri = "".toUri(),
                            displayName = "Me iré con ella2",
                            id = 0L,
                            artist = "Santa Fe Klan",
                            data = "",
                            duration = 267491,
                            title = "TITLE"
                        ),
                    )*/

                    println("HEY HEY aqui esta el tamaño antes de main" + procAudiosList.size)
                    MainScreen(storedAudiosList, procAudiosList)


                } else {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "Sin permiso para acceder al almacenamiento")
                    }
                }
            }
        }


    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(storedAudiosList: List<Audio>, processedAudiosList: MutableList<AudioProc>) {
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
    ){

        NavigationHost(navController,storedAudiosList, processedAudiosList)
    }
}


package com.example.DLChordsTT.features.audio_list.ui.screens

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.DLChordsTT.features.audio_list.navigation.Destinations
import com.example.DLChordsTT.features.audio_list.navigation.NavigationHostScreens
import com.example.DLChordsTT.features.audio_list.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioListActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DLChordsTheme {
                val permissionState = rememberMultiplePermissionsState(
                    listOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                    )
                )

                if (permissionState.allPermissionsGranted) {
                    val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()
                    MainScreen(generatedFilesViewModel)
                } else {
                    PermissionScreen(permissionState)
                }
            }

        }
    }
}


@Composable
fun MainScreen(generatedFilesViewModel:GeneratedFilesViewModel) {
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
        NavigationHostScreens(navController,  generatedFilesViewModel = generatedFilesViewModel)
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionScreen(permissionState: MultiplePermissionsState) {
    Scaffold(
        isFloatingActionButtonDocked = false,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Hacen falta permisos para que la aplicacion funcione correctamente, por favor haga click en el boton de abajo para otorgar dichos permisos.",
                modifier = Modifier.padding(vertical = 32.dp)
            )
            Button(
                onClick = {
                    permissionState.launchMultiplePermissionRequest() },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 40.dp),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),

                ) {
                Text(
                    text = "Dar permisos",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )
            }
        }
    }
}


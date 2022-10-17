package com.example.DLChordsTT.features.audio_list.ui.screens

import DLChordsTT.BuildConfig
import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
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
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DLChordsTheme {
                val context = LocalContext.current
                val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()
                if (Build.VERSION.SDK_INT >= 30) {
                    if (Environment.isExternalStorageManager()) {
                        MainScreen(generatedFilesViewModel)
                    } else {
                        PermissionScreen(context)
                    }
                } else {
                    val permissionState = rememberMultiplePermissionsState(
                        permissions =
                        listOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    )
                    if (permissionState.allPermissionsGranted) {
                        MainScreen(generatedFilesViewModel)
                    } else {
                        PermissionScreen(context, false, permissionState)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(generatedFilesViewModel: GeneratedFilesViewModel) {
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
        NavigationHostScreens(navController, generatedFilesViewModel = generatedFilesViewModel)
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun PermissionScreen(
    context: Context,
    isAndroidVersionElevenORPlus: Boolean = true,
    permissionState: MultiplePermissionsState? = null
) {
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
                text = "Hacen falta permisos para que la aplicacion funcione correctamente, " +
                        "haga click en el boton de abajo para otorgar dichos permisos.",
                modifier = Modifier.padding(vertical = 32.dp)
            )
            Button(
                onClick = {
                    if (isAndroidVersionElevenORPlus) {
                        val uri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                        val intent = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
                        startActivity(context, intent, null)
                    } else {
                        permissionState?.launchMultiplePermissionRequest()
                            ?: println("*********** Hubo un error con el launcher de permisos ***********")
                    }
                },
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
            if (isAndroidVersionElevenORPlus) {
                Text(
                    text = "Despues de otorgar los permisos reinicie la aplicación.",
                    modifier = Modifier.padding(vertical = 0.dp)
                )
            }
        }
    }
}


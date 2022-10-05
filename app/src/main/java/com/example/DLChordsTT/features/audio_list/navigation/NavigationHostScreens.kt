package com.example.DLChordsTT.features.audio_list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.DLChordsTT.features.audio_list.navigation.Destinations.*

import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.FileApiViewModel
import com.example.DLChordsTT.features.audio_list.ui.screens.ProcessedAudiosScreen
import com.example.DLChordsTT.features.audio_list.ui.screens.StoredAudiosScreen
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel


@Composable
fun NavigationHostScreens(
    navController: NavHostController,
    generatedFilesViewModel: GeneratedFilesViewModel
) {
    NavHost(navController = navController, startDestination = StoredAudios.route) {
        composable(StoredAudios.route) {
            val audioStoredViewModel: AudioViewModel = hiltViewModel()
            val fileApiViewModel: FileApiViewModel = hiltViewModel()
            StoredAudiosScreen(audioStoredViewModel = audioStoredViewModel, fileApiViewModel = fileApiViewModel)
        }
        composable(ProcessedAudios.route) {
            val audioProcessedViewModel: AudioProcViewModel = hiltViewModel()
            val state = audioProcessedViewModel.state.value
            val isRefreshing = audioProcessedViewModel.isRefreshing.collectAsState()

            ProcessedAudiosScreen(
                state = state,
                isRefreshing = isRefreshing.value,
                refreshData = audioProcessedViewModel::getAudiosProcessedBD,
                generatedFilesViewModel=generatedFilesViewModel,
                audioProcessedViewModel = audioProcessedViewModel
            )

        }
    }
}
package com.example.DLChordsTT.features.audio_lists.ui.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.DLChordsTT.features.audio_lists.navigation.Destinations.*
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc

import com.example.DLChordsTT.features.audio_lists.view_models.AudioViewModel
import com.example.DLChordsTT.features.generated_files.viewmodel.AudioProcViewModel


@Composable
fun NavigationHost(
    navController: NavHostController,
      audioViewModel: AudioViewModel,
) {
    NavHost(navController = navController, startDestination = StoredAudios.route) {
        composable(StoredAudios.route) {
            StoredAudiosScreen(audioViewModel = audioViewModel)
        }
        composable(ProcessedAudios.route) {
            val audioProcessedViewModel : AudioProcViewModel = hiltViewModel()
            val state = audioProcessedViewModel.state.value
            val isRefreshing = audioProcessedViewModel.isRefreshing.collectAsState()

            ProcessedAudiosScreen(state = state,isRefreshing = isRefreshing.value, refreshData = audioProcessedViewModel::getAudiosProcessedBD)

        }
    }
}
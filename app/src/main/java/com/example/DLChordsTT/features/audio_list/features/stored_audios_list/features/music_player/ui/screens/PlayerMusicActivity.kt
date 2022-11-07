package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models.PythonFlaskApiViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.music_player.ui.screens.PlayerMusicStored
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerMusicActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val receiveMusic = intent.extras
        val musicData = receiveMusic?.getInt("AudioId")
        var cont = 0
        val isAscending = receiveMusic?.getBoolean("isAscending")
        val isAlreadyProcessed = receiveMusic?.getBoolean("isAlreadyProcessed") ?: false


        super.onCreate(savedInstanceState)
        val audioViewModel: AudioViewModel by viewModels()
        if (isAscending != null) {
            audioViewModel.isAscending.value = isAscending
        }
        val storedAudiosList = audioViewModel.storedAudioList
        val audioProcViewModel: AudioProcViewModel by viewModels()
        val pythonFlaskApiViewModel: PythonFlaskApiViewModel by viewModels()
        val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()

        setContent {
            DLChordsTheme {
                Crossfade(targetState = audioViewModel.isLoading.value) {
                    if (!it) {
                        if (musicData != null && storedAudiosList.size != 0) {
                            cont+=1
                            if (!audioViewModel.isAudioPlaying && cont==1) {
                                audioViewModel.playAudio(storedAudiosList[musicData],false)
                            }

                            PlayerMusicStored(
                                progress = audioViewModel.currentAudioProgress.value,
                                onProgressChange = {},
                                audio = storedAudiosList[musicData],
                                audioViewModel = audioViewModel,
                                isAlreadyProcessed = isAlreadyProcessed,
                                context = LocalContext.current,
                                pythonFlaskApiViewModel =  pythonFlaskApiViewModel,
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }


}


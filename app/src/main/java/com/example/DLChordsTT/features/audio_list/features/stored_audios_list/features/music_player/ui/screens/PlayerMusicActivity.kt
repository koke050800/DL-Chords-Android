package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.music_player.ui.screens.PlayerMusicStored
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerMusicActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val receiveMusic = intent.extras
        val musicData = receiveMusic?.getInt("AudioId")

        super.onCreate(savedInstanceState)
        val audioViewModel: AudioViewModel by viewModels()
        val storedAudiosList = audioViewModel.storedAudioList

        setContent {
            DLChordsTheme {

                Crossfade(targetState = audioViewModel.isLoading.value) {
                    if (!it) {
                        if (musicData != null && storedAudiosList.size != 0) {

                            println("--**-*-*-*--*-*-////// ${storedAudiosList[musicData]}")
                            if (!audioViewModel.isPlaying.value) {
                                println("///////////// ID DE SHIT $musicData")
                                audioViewModel.playAudio(storedAudiosList[musicData])
                            }
                            PlayerMusicStored(
                                progress = audioViewModel.currentAudioProgress.value,
                                onProgressChange = {},
                                storedAudiosList[musicData],
                                audioViewModel = audioViewModel
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


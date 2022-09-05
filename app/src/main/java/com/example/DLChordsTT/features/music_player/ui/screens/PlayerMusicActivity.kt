package com.example.DLChordsTT.features.music_player.ui.screens

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.view_models.AudioViewModel
import com.example.DLChordsTT.features.music_player.ui.constants.K
import com.example.DLChordsTT.features.music_player.ui.screens.ui.theme.DLChordsTT1Theme
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.lang.Exception

@AndroidEntryPoint
class PlayerMusicActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = LocalContext
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

                            if (!audioViewModel.isPlaying.value) {
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

                // A surface container using the 'background' color from the theme
                //println("Size ${storedAudiosList.size}")


                if (musicData != null) {

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    DLChordsTheme {
        /*PlayerMusicStored(progress = 50f, onProgressChange = {}, audio = Audio(
            uri = "".toUri(),
            displayName = "Me iré con ella",
            id = 0L,
            artist = "Santa Fe Klan",
            data = "",
            duration = 765454,
            title = "TITLE"
        ) )*/
    }
}
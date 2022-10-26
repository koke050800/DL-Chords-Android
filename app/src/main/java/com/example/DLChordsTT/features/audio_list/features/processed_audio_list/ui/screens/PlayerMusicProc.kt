package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.ui.screens

import DLChordsTT.R
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessedAudio
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToDuration
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens.FilesBDShowActivity
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun PlayerMusicProcessed(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    audioP: AudioProc,
    audioViewModel: AudioViewModel,
    audioProcViewModel: AudioProcViewModel,
    generatedFilesViewModel: GeneratedFilesViewModel,
    isAlreadyProcessed: Boolean,
) {
    val openDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val toScreenPDF = Intent(context, FilesBDShowActivity::class.java)
    toScreenPDF.putExtra("Audio", audioP)


    DLChordsTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            ) {
            TopAppBarPlayer(textOnTop = audio.title, audio = audio, audioViewModel = audioViewModel, isBack = true)
            Card(
                shape = DLChordsTheme.shapes.medium,
                modifier = Modifier
                    .width(270.dp)
                    .height(300.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 38.dp, bottom = 25.dp),
                backgroundColor = Color(0xFFD9D9D9)
                ) {
                Image(
                    painter = painterResource(id = R.drawable.musicplayer_image),
                    "Player",
                )
            }
            val range = 0f..100f
            Column(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp)
            ) {
                Slider(
                    value = progress,
                    valueRange = range,
                    onValueChange = { onProgressChange.invoke(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = DLChordsTheme.colors.secondaryText,
                        activeTrackColor = DLChordsTheme.colors.secondaryText
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = timeStampToDuration((progress.toLong() * audio.duration) / 100), style = DLChordsTheme.typography.caption)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = timeStampToDuration(audio.duration.toLong()),
                        style = DLChordsTheme.typography.caption
                    )
                }
            }

            AlertDialogProcessedAudio(openDialogProcessedAudio = openDialog)

            Button(
                onClick = {
                    generatedFilesViewModel.toCardScreen(
                        context,
                        toScreenPDF
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 40.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),

                ) {
                Text(
                    text = "ARCHIVOS",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )
            }
        }

    }
}
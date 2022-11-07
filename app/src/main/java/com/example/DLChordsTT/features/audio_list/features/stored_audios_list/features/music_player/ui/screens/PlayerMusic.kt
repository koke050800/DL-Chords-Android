package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.cut_audio.ui.screens.CutAnAudioActivity
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models.PythonFlaskApiViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogErrorResponse
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessedAudio
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessing
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToDuration
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens.FilesBDUploadActivity
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.launch


@Composable
fun PlayerMusicStored(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    audioViewModel: AudioViewModel,
    isAlreadyProcessed: Boolean,
    context: Context,
    pythonFlaskApiViewModel: PythonFlaskApiViewModel,
) {
    val openDialog = remember { mutableStateOf(false) }
    val openDialogError = remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()
    val openDialogProcessing = remember { mutableStateOf(false) }
    val pdfScreenIntent =
        Intent(
            context,
            FilesBDUploadActivity::class.java
        ) // TODO: quitar holis activity y poner la de pdfs
    val activity = (LocalContext.current as? Activity)

    DLChordsTheme {

        var cursorProgress by remember { mutableStateOf(0f) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            TopAppBarPlayer(
                textOnTop = audio.title,
                audio = audio,
                audioViewModel = audioViewModel,
                isBack = true
            )

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
                    onValueChange = {
                        onProgressChange.invoke(it)
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = DLChordsTheme.colors.secondaryText,
                        activeTrackColor = DLChordsTheme.colors.secondaryText
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = timeStampToDuration((progress.toLong() * audio.duration) / 100),
                        style = DLChordsTheme.typography.caption
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = timeStampToDuration(audio.duration.toLong()),
                        style = DLChordsTheme.typography.caption
                    )
                }
            }

            AlertDialogProcessedAudio(openDialogProcessedAudio = openDialog)
            AlertDialogErrorResponse(
                openDialogError = openDialogError,
                errorString = "Error de conexión con el servidor"
            )

            Text(
                modifier = Modifier.padding(start = 45.dp),
                textAlign = TextAlign.Left,
                text = "Opciones de procesamiento",
                style = DLChordsTheme.typography.h5,
                maxLines = 1
            )



            Button(
                onClick = {
                    if (!isAlreadyProcessed) {
                        audioViewModel.playAudio(audio, false)

                        audioViewModel.playAudio(audio, true)
                        audioViewModel.isPlayingAgain.value = true
                        val cutAudio = Intent(context, CutAnAudioActivity::class.java)
                        cutAudio.putExtra("AudioId", audio.id)
                        cutAudio.putExtra("isAscending", audioViewModel.isAscending.value)
                        context.startActivity(cutAudio)
                        activity?.finish()

                    } else {
                        openDialog.value = true
                    }

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(vertical = 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(1.dp, color = DLChordsTheme.colors.divider, shape = CircleShape),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DLChordsTheme.colors.background,
                )
            ) {
                Text(
                    text = "FRAGMENTO DE AUDIO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.primary
                )
            }


            Button(
                onClick = {

                    if (!isAlreadyProcessed) {
                        scope.launch {
                            pythonFlaskApiViewModel.uploadAudio(audio)
                        }
                    } else {
                        openDialog.value = true
                    }

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 40.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),

                ) {
                pythonFlaskApiViewModel.isScopeCompleted.value?.let { isScopeCompleted ->
                    if (!isScopeCompleted) {
                        openDialogProcessing.value = true
                        Text(
                            "Procesando...",
                            maxLines = 2,
                            style = DLChordsTheme.typography.button,
                            color = DLChordsTheme.colors.surface
                        )
                    } else {

                        var response = pythonFlaskApiViewModel.responseUploadAudio?.value
                            ?: "RESPONSE NULL DESDE PREDICCION EN PLAYER MUSIC"
                        if (response.contains("RESPONSE NULL DESDE PREDICCION")) {
                            openDialogProcessing.value = false //cerrar el progressIndicator
                            openDialogError.value = true
                        } else {

                            openDialogProcessing.value = false //cerrar el progressIndicator


                            pdfScreenIntent.putExtra("response", response)

                            var audioP = AudioProc(
                                id = audio.id,
                                displayName = audio.displayName,
                                artist = audio.artist,
                                data = audio.data,
                                duration = audio.duration,
                                title = audio.title,
                                english_nomenclature = "",
                                latin_nomenclature = "",
                                chords_lyrics_e = "",
                                chords_lyrics_l = "",
                                lyrics = "",
                            )

                            pdfScreenIntent.putExtra("Audio", audioP)

                            startActivity(context, pdfScreenIntent, null)
                        }
                    }
                } ?: Text(
                    text = "AUDIO COMPLETO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )
            }
            AlertDialogProcessing(openDialogProcessing = openDialogProcessing)
        }

    }
}

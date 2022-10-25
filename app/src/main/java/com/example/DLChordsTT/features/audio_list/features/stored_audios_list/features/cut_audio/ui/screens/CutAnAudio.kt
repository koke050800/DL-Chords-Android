package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.cut_audio.ui.screens

import DLChordsTT.R
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models.PythonFlaskApiViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessing
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToDuration
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToSeconds
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens.holiActivity
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CutAnAudioScreen(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    audioViewModel: AudioViewModel,
    pythonFlaskApiViewModel: PythonFlaskApiViewModel,
) {
    val openDialogProcessing = remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()

    val context = LocalContext.current
    val pdfScreenIntent =
        Intent(context, holiActivity::class.java) // TODO: quitar holis activity y poner la de pdfs

    DLChordsTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

            ) {
            TopAppBarPlayer(
                textOnTop = "Pantalla de Recorte",
                audio = audio,
                audioViewModel = audioViewModel,
                true
            )
            Card(
                shape = DLChordsTheme.shapes.medium,
                modifier = Modifier
                    .width(254.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 38.dp, bottom = 25.dp),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.musicplayer_image),
                    "Player",
                )
            }
            val range = 0f..100f
            var select by remember { mutableStateOf(range) }
            var changedSlider by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp)
            ) {
                Box {
                    Slider(
                        value = progress,
                        valueRange = range,
                        onValueChange = { onProgressChange.invoke(it) },
                        colors = SliderDefaults.colors(
                            thumbColor = DLChordsTheme.colors.secondaryText,
                            activeTrackColor = DLChordsTheme.colors.secondaryText
                        )
                    )
                    RangeSlider(
                        values = select,
                        valueRange = range,
                        onValueChange = {
                            select = it
                            changedSlider = true
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    if (changedSlider) {

                        Card(
                            backgroundColor = DLChordsTheme.colors.primary, modifier = Modifier
                                .fillMaxHeight(0.1f)
                                .fillMaxWidth(0.2f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = timeStampToDuration((select.start.toLong() * audio.duration) / 100),
                                    style = DLChordsTheme.typography.caption,
                                    color = DLChordsTheme.colors.onPrimary
                                )
                            }
                        }
                    } else {
                        Text(
                            text = timeStampToDuration((select.start.toLong() * audio.duration) / 100),
                            style = DLChordsTheme.typography.caption
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (changedSlider) {
                        Card(
                            backgroundColor = DLChordsTheme.colors.primary, modifier = Modifier
                                .fillMaxHeight(0.1f)
                                .fillMaxWidth(0.2f)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = timeStampToDuration((select.endInclusive.toLong() * audio.duration) / 100),
                                    style = DLChordsTheme.typography.caption,
                                    color = DLChordsTheme.colors.onPrimary
                                )
                            }
                        }
                    } else {
                        Text(
                            text = timeStampToDuration((select.endInclusive.toLong() * audio.duration) / 100),
                            style = DLChordsTheme.typography.caption
                        )
                    }
                }
            }

            AlertDialogProcessing(openDialogProcessing = openDialogProcessing)

            Button(
                onClick = {
                    var timeInitial = (select.start.toLong() * audio.duration) / 100
                    var timeEnd = (select.endInclusive.toLong() * audio.duration) / 100
                    println("De ${timeStampToSeconds(timeInitial)} a ${timeStampToSeconds(timeEnd)}")

                    scope.launch {
                        pythonFlaskApiViewModel.uploadAudioAndCut(
                            audio,
                            timeStampToSeconds(timeInitial),
                            timeStampToSeconds(timeEnd)
                        )
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
                            ?: "RESPONSE NULL DESDE PREDICCION CUT AUDIO"
                        openDialogProcessing.value = false //cerrar el progressIndicator
                        pdfScreenIntent.putExtra("response", response)

                        //datos del audio
                        pdfScreenIntent.putExtra("AudioProc_id", audio.id)
                        pdfScreenIntent.putExtra("AudioProc_displayName", audio.displayName)
                        pdfScreenIntent.putExtra("AudioProc_artist", audio.artist)
                        pdfScreenIntent.putExtra("AudioProc_data", audio.data)
                        pdfScreenIntent.putExtra("AudioProc_duration", audio.duration)
                        pdfScreenIntent.putExtra("AudioProc_title", audio.title)

                        //lanzamos actividad
                        ContextCompat.startActivity(context, pdfScreenIntent, null)
                    }
                } ?: Text(
                    text = "PROCESAR SELECCION",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )

            }
        }

    }
}
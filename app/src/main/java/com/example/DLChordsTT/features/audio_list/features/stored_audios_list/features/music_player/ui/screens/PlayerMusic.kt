package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models.PythonFlaskApiViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessedAudio
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessing
import com.example.DLChordsTT.features.audio_list.ui.components.timeStampToDuration
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens.holiActivity
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
    audioProcViewModel: AudioProcViewModel,
    generatedFilesViewModel: GeneratedFilesViewModel,
    isAlreadyProcessed: Boolean,
    context: Context,
    pythonFlaskApiViewModel: PythonFlaskApiViewModel,
) {
    val openDialog = remember { mutableStateOf(false) }
    var scope = rememberCoroutineScope()
    val openDialogProcessing = remember { mutableStateOf(false) }
    val pdfScreenIntent =
        Intent(context, holiActivity::class.java) // TODO: quitar holis activity y poner la de pdfs
    //val cutScreenIntent = Intent(context, /*TODO: PONER ACTIVIDAD DE RECORTE*/)

    DLChordsTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
            ) {
            TopAppBarPlayer(textOnTop = audio.title, audio = audio, audioViewModel = audioViewModel)

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
                    Text(text = "0:00", style = DLChordsTheme.typography.caption)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = timeStampToDuration(audio.duration.toLong()),
                        style = DLChordsTheme.typography.caption
                    )
                }
            }

            AlertDialogProcessedAudio(openDialogProcessedAudio = openDialog)

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
                        /////////Esto se hará cuando se termine de procesar el audio
                        val audioP = AudioProc(
                            id = audio.id,
                            displayName = audio.displayName,
                            artist = audio.artist,
                            data = audio.data,
                            duration = audio.duration,
                            title = audio.title,
                        )
                        audioProcViewModel.addNewAudioProc(audioP)
                        generatedFilesViewModel.generatePDFs(
                            context = context,
                            audioProc = audioP,
                            chordsJson = "",
                            wordsJson = " "
                        )

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
                        startActivity(context, pdfScreenIntent, null)
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

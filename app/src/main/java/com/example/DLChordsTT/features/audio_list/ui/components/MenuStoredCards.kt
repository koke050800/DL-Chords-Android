package com.example.DLChordsTT.features.audio_list.ui.components

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.cut_audio.ui.screens.CutAnAudioActivity
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models.PythonFlaskApiViewModel
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens.holiActivity
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MenuStoredCards(
    audio: Audio,
    pythonFlaskApiViewModel: PythonFlaskApiViewModel,
    alreadyProcessedAudiosList: List<AudioProc>,
    expandedMenu: MutableState<Boolean>,
    openDialogProcessedAudio: MutableState<Boolean>,
    openDialogProcessing: MutableState<Boolean>,
    isAscending: Boolean
) {
    val context = LocalContext.current
    val pdfScreenIntent =
        Intent(context, holiActivity::class.java) // TODO: quitar holis activity y poner la de pdfs
    val cutScreenIntent = Intent(context, CutAnAudioActivity::class.java)

    DropdownMenu(
        expanded = expandedMenu.value,
        onDismissRequest = { expandedMenu.value = false }
    ) {

        var scope = rememberCoroutineScope()
        var isAlreadyProcessedInBD by remember {
            mutableStateOf(false)
        }

        DropdownMenuItem(onClick = {

            isAlreadyProcessedInBD = false
            alreadyProcessedAudiosList.forEach {
                if (it.title.lowercase(locale = Locale.getDefault())
                    == audio.title.lowercase(locale = Locale.getDefault())
                ) {
                    isAlreadyProcessedInBD = true
                }
            }

            if (!isAlreadyProcessedInBD) {
                scope.launch {
                    pythonFlaskApiViewModel.uploadAudio(audio)
                }
            } else {
                openDialogProcessedAudio.value = true
            }

        }) {
            Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.Start) {
                pythonFlaskApiViewModel.isScopeCompleted.value?.let { isScopeCompleted ->
                    if (!isScopeCompleted) {
                        openDialogProcessing.value = true
                        Text(
                            "Procesando...",
                            style = DLChordsTheme.typography.caption,
                            maxLines = 2
                        )
                    } else {

                        var response = pythonFlaskApiViewModel.responseUploadAudio?.value
                            ?: "RESPONSE NULL DESDE PREDICCION"
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
                } ?: Text("Procesar audio completo", style = DLChordsTheme.typography.caption)
            }
        }


        Divider()


        DropdownMenuItem(onClick = {

            isAlreadyProcessedInBD = false
            alreadyProcessedAudiosList.forEach {
                if (it.title.lowercase(locale = Locale.getDefault())
                    == audio.title.lowercase(locale = Locale.getDefault())
                ) {
                    isAlreadyProcessedInBD = true
                }
            }

            if (!isAlreadyProcessedInBD) {
                cutScreenIntent.putExtra("AudioId",audio.id)
                cutScreenIntent.putExtra("isAscending",isAscending)
                startActivity(context, cutScreenIntent, null)
            } else {
                openDialogProcessedAudio.value = true
            }

        }) {
            Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.Start) {
                Text("Procesar fragmento de audio", style = DLChordsTheme.typography.caption)
            }
        }
    }
}


package com.example.DLChordsTT.features.audio_list.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.FileApiViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MenuStoredCards(
    audio: Audio,
    fileApiViewModel: FileApiViewModel,
    alreadyProcessedAudiosList: List<AudioProc>,
    expandedMenu: MutableState<Boolean>,
    openDialogProcessedAudio: MutableState<Boolean>,
    openDialogProcessing: MutableState<Boolean>,
) {
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
                    fileApiViewModel.uploadAudio(audio)
                }
            } else {
                openDialogProcessedAudio.value = true
            }

        }) {
            Column(modifier = Modifier.fillMaxWidth(1f), horizontalAlignment = Alignment.Start) {
                fileApiViewModel.isScopeCompleted.value?.let {
                    if (!it) {
                        openDialogProcessing.value = true
                        Text("Procesando...", style = DLChordsTheme.typography.caption, maxLines = 2)
                    } else {
                        //AQUI vamos a mandar la actividad de los pdfs
                        println("---Termine ${fileApiViewModel.responseUploadAudio.value}")


                        ///AQUI PUEDES GENERAR LOS PDF FER o no se donde lo hagas jajajaj


                        //aqui cerramos el progress
                        openDialogProcessing.value = false //cerrar el progressindicator
                        fileApiViewModel.isScopeCompleted.value = null //para que la vista regrese a la manera original
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
                //AQUI SOLO SE DESPLIEGA ACTIVIDAD DE RECORTE
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


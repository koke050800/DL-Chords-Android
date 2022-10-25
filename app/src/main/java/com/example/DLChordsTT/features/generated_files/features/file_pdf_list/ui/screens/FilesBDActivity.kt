package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessedAudio
import com.example.DLChordsTT.features.audio_list.ui.components.AlertDialogProcessing
import com.example.DLChordsTT.features.audio_list.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.audio_list.ui.components.formatDuration
import com.example.DLChordsTT.features.audio_list.ui.screens.FilesBDScreen
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.music_player.ui.screens.PlayerMusicStored
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.*

@AndroidEntryPoint
class FilesBDActivity : ComponentActivity() {
    val audioViewModel: AudioProcViewModel by viewModels()
    val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()
    val audioProcViewModel :AudioProcViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val receiveName = intent.extras
        var responseWhitLyricChords = receiveName?.getString("response")
        val audio = receiveName?.getSerializable("Audio") as AudioProc

        super.onCreate(savedInstanceState)


       // val receivePredictionResponse = intent.extras


        //Datos del audio para subir a la base de datos
      /*  var id:Long = receivePredictionResponse?.getLong("AudioProc_id") ?: 0
        var displayName = receivePredictionResponse?.getString("AudioProc_displayName") ?: "display name null"
        var artist = receivePredictionResponse?.getString("AudioProc_artist")?: "artist null"
        var data = receivePredictionResponse?.getString("AudioProc_data")?: "data null"
        var duration = receivePredictionResponse?.getInt("AudioProc_duration") ?: 0
        var title = receivePredictionResponse?.getString("AudioProc_title")?: "title null"*/

        var audioP = AudioProc(
            id = audio.id,
            displayName = audio.displayName,
            artist = audio.artist,
            data = audio.data,
            duration = audio.duration,
            title = audio.title,
            english_nomenclature = audio.english_nomenclature,
            latin_nomenclature = audio.latin_nomenclature ,
            chords_lyrics_e = audio.chords_lyrics_e,
            chords_lyrics_l = audio.chords_lyrics_l,
            lyrics = audio.lyrics,

        )

        setContent {

            DLChordsTheme {
               Scaffold(
                    isFloatingActionButtonDocked = false,
                    floatingActionButtonPosition = FabPosition.End,
                    backgroundColor = MaterialTheme.colors.background
                ){
                 /*
                   LaunchedEffect(Unit) {
                       delay(10000)
                       if (responseWhitLyricChords != null) {
                           audioViewModel.addNewAudioProc(audioP)
                           println("Ya subi el Audio sin archivos ")
                           println("Estoy generando PDF's desde ActivityFilesBD")
                           generatedFilesViewModel.generatePDFs(
                               context, audioP.id,
                               audioP.displayName, audioP.artist,
                               audioP.data, audioP.duration,
                               audioP.title, "", "", "", "", "", responseWhitLyricChords
                           )
                       }



                       var state = generatedFilesViewModel.state.value
                       println("Tam: ${state.audioProcessedList.size}")
                       if (state.audioProcessedList.isNotEmpty()) {
                           println("Tam: ${state.audioProcessedList.size}")
                           for (item in state.audioProcessedList) {
                               if (item.id == audioP.id) {
                                   println("Entre aqui $item")
                                   audioP = item
                                   break
                               } else {
                                   println("Entre aca ")

                               }
                           }
                       }
                   }*/


                 /*  Crossfade(targetState = generatedFilesViewModel.isLoading.value) {
                       if (!it) {
                           if (responseWhitLyricChords != null) {
                               audioViewModel.addNewAudioProc(audioP)

                               generatedFilesViewModel.generatePDFs(
                                   LocalContext.current, audioP.id,
                                   audioP.displayName, audioP.artist,
                                   audioP.data, audioP.duration,
                                   audioP.title, "", "", "", "", "", responseWhitLyricChords
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
                   }*/




                   FilesBDScreen(audio = audioP , generatedFilesViewModel)


                   /*  Crossfade(targetState = generatedFilesViewModel.isCompleted.value) {
                            if (!it) {

                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }

                            } else {


                            }
                        }

     */




                }
            }    
        }
        
    }
}
package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.ui.screens.FilesBDScreen
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilesBDShowActivity : ComponentActivity() {
    val audioViewModel: AudioProcViewModel by viewModels()
    val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()
    val audioProcViewModel: AudioProcViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val receiveName = intent.extras
        val audio = receiveName?.getSerializable("Audio") as AudioProc

        super.onCreate(savedInstanceState)

        setContent {


            DLChordsTheme {
                Scaffold(
                    isFloatingActionButtonDocked = false,
                    floatingActionButtonPosition = FabPosition.End,
                    backgroundColor = MaterialTheme.colors.background
                ) {


                    FilesBDScreen(audio = audio, generatedFilesViewModel)
                }
            }
        }

    }
}
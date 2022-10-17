package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.ui.screens.FilesBDScreen
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.generated_files.ui.components.joinCardsPDF
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPDF
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class holiActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val receiveName = intent.extras
        val audioName = receiveName?.getSerializable("AudioName") as AudioProc
        super.onCreate(savedInstanceState)
        val generatedFilesViewModel: GeneratedFilesViewModel by viewModels()

        setContent {
            DLChordsTheme {

                Scaffold(
                    isFloatingActionButtonDocked = false,
                    floatingActionButtonPosition = FabPosition.End,
                    backgroundColor = MaterialTheme.colors.background
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Holis")
                    }
                }
            }
        }

    }
}
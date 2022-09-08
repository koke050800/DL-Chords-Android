package com.example.DLChordsTT.features.audio_list.ui.screens

import android.text.Layout
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProcessedListState
import com.example.DLChordsTT.features.audio_list.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_list.ui.components.LabelAndDividerOfListsPDF
import com.example.DLChordsTT.features.audio_list.ui.components.ProcessedCard
import com.example.DLChordsTT.features.audio_list.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.features.generated_files.ui.components.CardPDF
import com.example.DLChordsTT.features.generated_files.ui.components.joinCardsPDF
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPDF
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun FilesBDScreen( audioName:String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBarPDF("Archivos Generados - ${audioName}")

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            item {
                joinCardsPDF("Letra", 1)
            }

            item {
                joinCardsPDF("Nomenclatura Inglesa", 2)
            }

            item {
                joinCardsPDF("Nomenclatura Latina", 2)
            }


        }


    }
}
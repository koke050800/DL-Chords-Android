package com.example.DLChordsTT.features.audio_lists.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.ui.components.CartaListaAlmacenados
import com.example.DLChordsTT.features.audio_lists.ui.components.CartaListaProcesados
import com.example.DLChordsTT.features.audio_lists.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_lists.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.audio_lists.view_models.AudioViewModel
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.viewmodel.AudioProcViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun ProcessedAudiosScreen(audioProcViewModel: AudioProcViewModel = hiltViewModel()) {
    var processedAudioList = audioProcViewModel.state.value.audioProcessedList
        println("Tamañítoooooou" +  processedAudioList.size)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = audioProcViewModel.isRefreshing.collectAsState().value),
            onRefresh = {
                audioProcViewModel::getAudiosProcessedBD
            }
        ) {
            LazyColumn() {
                item { LabelAndDividerOfLists(label = "Audios Procesados") }
                if (processedAudioList.isNotEmpty()) {
                    items(processedAudioList) { audioElementList: AudioProc ->
                        CartaListaProcesados(audio = audioElementList)
                        /*AudioItem(
                            audio = audioElementList,
                            onItemClick = { onItemClick.invoke(audioElementList)},
                        )*/
                    }
                } else {
                    item {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center)
                            ) {
                                Text(
                                    text = "No se ha procesado ningún audio",
                                    style = DLChordsTheme.typography.h5,
                                    color = DLChordsTheme.colors.primaryText,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}
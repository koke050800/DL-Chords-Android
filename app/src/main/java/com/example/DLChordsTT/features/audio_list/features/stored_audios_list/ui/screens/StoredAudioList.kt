package com.example.DLChordsTT.features.audio_list.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.ui.components.StoredCard
import com.example.DLChordsTT.features.audio_list.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_list.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh

@Composable
fun StoredAudiosScreen(audioViewModel: AudioViewModel = hiltViewModel()) {
    var storedAudioList = audioViewModel.storedAudioList

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        SwipeRefresh(
            state = audioViewModel.isRefreshing,
            onRefresh = {
                audioViewModel.getStoredAudios2()
            }
        ) {
            LazyColumn() {
                item { LabelAndDividerOfLists(label = "Audios Almacenados") }
                if (storedAudioList.isNotEmpty()) {
                    items(storedAudioList) { audioElementList: Audio ->
                        StoredCard(audio = audioElementList)
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
                                    text = "No hay audios almacenados en la carpeta \"DLChords\"",
                                    style = DLChordsTheme.typography.h5,
                                    color = DLChordsTheme.colors.primaryText,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                                Text(
                                    text = "Esta carpeta esta ubicada en\n\"/storage/emulated/0/Music/DLChords\"",
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


package com.example.DLChordsTT.features.audio_list.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.ui.components.ProcessedCard
import com.example.DLChordsTT.features.audio_list.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_list.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProcessedListState
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*



@Composable
fun ProcessedAudiosScreen(
    state: AudioProcessedListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    generatedFilesViewModel: GeneratedFilesViewModel,
    audioProcessedViewModel: AudioProcViewModel,
    audioViewModel: AudioViewModel
) {
    // var processedAudioList = audioStoredViewModel.storedAudioList
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(
            state = textState,
            focusManager = focusManager,
            onClick = {
                audioProcessedViewModel.isDescending.value =
                    !audioProcessedViewModel.isDescending.value
            }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = refreshData,
        ) {
            LazyColumn() {
                item { LabelAndDividerOfLists(label = "Audios Procesados") }




                if (state.audioProcessedList.isNotEmpty()) {

                    val searchedText = textState.value.text
                    var processedAudioListFiltered = if (searchedText.isEmpty()) {
                        if (audioProcessedViewModel.isDescending.value) state.audioProcessedList else state.audioProcessedListInverted
                    } else {
                        val resultList = mutableListOf<AudioProc>()
                        for (audioProcessed in if (audioProcessedViewModel.isDescending.value) state.audioProcessedList else state.audioProcessedListInverted) {
                            if (audioProcessed.title.lowercase(Locale.getDefault())
                                    .contains(searchedText.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(audioProcessed)
                            }
                        }
                        resultList
                    }



                    items(items = processedAudioListFiltered) { audioElementList: AudioProc ->
                        ProcessedCard(
                            audio = audioElementList,
                            index = audioElementList.id,
                            isAscending = audioViewModel.isAscending.value,
                            generatedFilesViewModel = generatedFilesViewModel
                        )
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

        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally),
                text = state.error,
                color = DLChordsTheme.colors.primary,
                textAlign = TextAlign.Center
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

    }
}
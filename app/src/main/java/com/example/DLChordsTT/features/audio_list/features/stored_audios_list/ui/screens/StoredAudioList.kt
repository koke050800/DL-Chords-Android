package com.example.DLChordsTT.features.audio_list.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProcessedListState
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.FileApiViewModel
import com.example.DLChordsTT.features.audio_list.ui.components.StoredCard
import com.example.DLChordsTT.features.audio_list.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_list.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import java.util.*

@Composable
fun StoredAudiosScreen(
    audioStoredViewModel: AudioViewModel,
    fileApiViewModel: FileApiViewModel,
    alreadyProccessedAudios: AudioProcViewModel,
    stateAlreadyProccessedAudios : AudioProcessedListState,
) {
    var storedAudioList = audioStoredViewModel.storedAudioList
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    var alreadyProccessedAudiosList =  stateAlreadyProccessedAudios.audioProcessedList


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
                audioStoredViewModel.isAscending.value =
                    !audioStoredViewModel.isAscending.value
                audioStoredViewModel.getStoredAudios()
            }
        )
        SwipeRefresh(
            state = audioStoredViewModel.isRefreshing,
            onRefresh = {
                audioStoredViewModel.getStoredAudios()
                alreadyProccessedAudios.getAudiosProcessedBD()
            }
        ) {
            LazyColumn() {
                item { LabelAndDividerOfLists(label = "Audios Almacenados") }
                if (storedAudioList.isNotEmpty()) {
                    val searchedText = textState.value.text
                    var storedAudioListFiltered = if (searchedText.isEmpty()) {
                        storedAudioList
                    } else {
                        val resultList = SnapshotStateList<Audio>()
                        for (audioStored in storedAudioList) {
                            if (audioStored.title.lowercase(Locale.getDefault())
                                    .contains(searchedText.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(audioStored)
                            }
                        }
                        resultList
                    }
                    items(storedAudioListFiltered) { audioElementList: Audio ->
                        StoredCard(
                            audio = audioElementList,
                            indexAudio = storedAudioList.indexOf(audioElementList),
                            isAscending = audioStoredViewModel.isAscending.value,
                            fileApiViewModel = fileApiViewModel,
                            alreadyProccessedAudiosList = alreadyProccessedAudiosList,
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


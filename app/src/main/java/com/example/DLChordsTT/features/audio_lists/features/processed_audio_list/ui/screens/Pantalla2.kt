package com.example.DLChordsTT.features.audio_lists.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_lists.ui.components.CartaListaProcesados
import com.example.DLChordsTT.features.audio_lists.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_lists.ui.components.SearchAndSortBar
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@Composable
fun ProcessedAudiosScreen(processedAudioList: MutableList<AudioProc>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        LazyColumn() {
            item { LabelAndDividerOfLists(label = "Audios Procesados") }
            if (processedAudioList.isNotEmpty()){
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
                        Column(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                            Text(
                                text = "No hay audios procesados aún",
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
@Preview(showBackground = true)
@Composable
fun ProcessedListPreview() {
    var audioListForPreview = mutableListOf<AudioProc>()

    for (i in 0..12) audioListForPreview.add(
        index = i,
        AudioProc(
           // uri = "".toUri(),
            displayName = "Me iré con ella",
            id = 0L,
            artist = "Santa Fe Klan",
            data = "",
            duration = 8765454,
            title = "TITLE"
        ),
    )

    DLChordsTheme {
        ProcessedAudiosScreen(audioListForPreview)
    }
}
package com.example.DLChordsTT.features.audio_lists.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.ui.components.CartaListaAlmacenados
import com.example.DLChordsTT.features.audio_lists.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_lists.ui.components.SearchAndSortBar
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun Pantalla2(storedAudioList: List<Audio>) {
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
            items(storedAudioList) { audioElementList: Audio ->
                CartaListaAlmacenados(audio = audioElementList,1)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProcessedListPreview() {
    var audioListForPreview = mutableListOf<Audio>()

    for (i in 0..12) audioListForPreview.add(
        index = i,
        Audio(
            uri = "".toUri(),
            displayName = "Me iré con ella",
            id = 0L,
            artist = "Santa Fe Klan",
            data = "",
            duration = 8765454,
            title = "TITLE"
        ),
    )

    DLChordsTheme {
        Pantalla2(audioListForPreview)
    }
}
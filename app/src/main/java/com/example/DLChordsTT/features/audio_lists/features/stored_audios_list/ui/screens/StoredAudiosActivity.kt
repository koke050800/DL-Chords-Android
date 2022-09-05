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
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.ui.components.CartaListaAlmacenados
import com.example.DLChordsTT.features.audio_lists.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.audio_lists.ui.components.SearchAndSortBar
import com.example.DLChordsTT.ui.theme.DLChordsTheme

/*
@AndroidEntryPoint
class StoredAudioActivity : AppCompatActivity() {
    private lateinit var audioViewModel: AudioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val audioViewModel: AudioViewModel by viewModels()
        val storedAudiosList = audioViewModel.storedAudioList

        setContent {
            StoredAudiosScreen(storedAudiosList)
        }
    }
}*/

@Composable
fun StoredAudiosScreen(storedAudioList: List<Audio>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        LazyColumn() {
            item { LabelAndDividerOfLists(label = "Audios Almacenados") }
            if (storedAudioList.isNotEmpty()){
                items(storedAudioList) { audioElementList: Audio ->
                    CartaListaAlmacenados(audio = audioElementList, indexAudio = storedAudioList.indexOf(audioElementList))
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

@Preview(showBackground = true)
@Composable
fun StoredListPreview() {
    var audioListForPreview = mutableListOf<Audio>()

    for (i in 0..12) audioListForPreview.add(
        index = i,
        Audio(
            uri = "".toUri(),
            displayName = "Me iré con ella",
            id = 0L,
            artist = "Santa Fe Klan",
            data = "",
            duration = 765454,
            title = "TITLE"
        ),
    )

    DLChordsTheme {
        StoredAudiosScreen(audioListForPreview)
    }
}
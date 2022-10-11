package com.example.DLChordsTT.features.generated_files.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FileDownload
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme



@Composable
fun joinCardsPDF(audio: AudioProc, label: String = "Label", nCards: Int = 1,generatedFilesViewModel : GeneratedFilesViewModel, pre: String = "E") {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
    ) {
        Column() {
            Row(Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = label,
                    style = DLChordsTheme.typography.h5,
                    color = DLChordsTheme.colors.primaryText,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
            if (nCards == 1) {
                CardPDF(audio.lyrics, "Letra ", "Disfruta de la letra del audio",generatedFilesViewModel)
            } else {
                if (pre.equals("E")) {
                    Row(modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 4.dp)) {
                        CardPDF(
                            audio.chords_lyrics_e,
                            "Letra y Acordes",
                            "Disfruta de los acordes y letra del audio",generatedFilesViewModel
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                        CardPDF(
                            audio.english_nomenclature,
                            "Acordes",
                            "Disfruta de los acordes  del audio",generatedFilesViewModel
                        )
                    }
                } else {
                    Row(modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 4.dp)) {
                        CardPDF(
                            audio.chords_lyrics_l,
                            "Letra y Acordes",
                            "Disfruta de los acordes y letra del audio",generatedFilesViewModel
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                        CardPDF(
                            audio.latin_nomenclature,
                            "Acordes",
                            "Disfruta de los acordes  del audio",generatedFilesViewModel
                        )
                    }
                }
            }
        }

    }


}


@Composable
fun CardPDF(url: String, title: String, text: String, generatedFilesViewModel: GeneratedFilesViewModel) {
    val context = LocalContext.current

    DLChordsTheme() {
        Card(
            modifier = Modifier
                .height(224.dp)
                .width(160.dp),
            backgroundColor = DLChordsTheme.colors.cardColor,
            shape = DLChordsTheme.shapes.medium
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = DLChordsTheme.typography.subtitle1,
                        maxLines = 1
                    )
                    ImageOfCardPDF(title)
                    Text(
                        text = text,
                        fontSize = 12.sp,
                        maxLines = 3
                    )
                }
                Row(modifier = Modifier.height(24.dp)) {
                    IconButton(
                        onClick = {
                            generatedFilesViewModel.showPDF(url, context)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Visibility,
                            contentDescription = "Visualizar"
                        )
                    }
                    IconButton(
                        onClick = {
                            generatedFilesViewModel.downloadPDF(url, context)
                        }
                    ) {
                        Icon(
                            Icons.Rounded.FileDownload,
                            contentDescription = "Descarga"
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ImageOfCardPDF(title: String) {
    val elementOfPainter = when (title) {
        "Letra" -> DLChordsTT.R.drawable.lyrics_image
        "Letra y Acordes" -> DLChordsTT.R.drawable.lyrics_chords_image
        "Acordes" -> DLChordsTT.R.drawable.chords_image
        else -> DLChordsTT.R.drawable.lyrics_image
    }
    Image(
        painterResource(elementOfPainter),
        "Imagen",
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)
    )
}


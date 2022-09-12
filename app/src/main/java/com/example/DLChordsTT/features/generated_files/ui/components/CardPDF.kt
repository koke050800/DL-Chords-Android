package com.example.DLChordsTT.features.generated_files.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.FileDownload
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun joinCardsPDF(label: String = "Label", nCards : Int = 1){
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
            if (nCards == 1){
            CardPDF("Letra ", "Disfruta de la letra del audio")}
            else{
                Row(modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 4.dp)) {
                    CardPDF("Letra y Acordes", "Disfruta de los acordes y letra del audio")
                    Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                    CardPDF("Acordes", "Disfruta de los acordes  del audio")
                }
            }
        }

    }




}


@Composable
fun CardPDF(title:String, text :String) {
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
                            //TODO
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Visibility,
                            contentDescription = "Visualizar"
                        )
                    }
                    IconButton(
                        onClick = {
                            //TODO
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
    val elementOfPainter = when(title){
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


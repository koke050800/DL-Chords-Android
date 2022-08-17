package com.example.DLChordsTT.features.generated_files.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
@Preview(showBackground = false)
fun CardPDF() {
    DLChordsTheme() {
        Card(
            modifier = Modifier
                .height(224.dp)
                .width(160.dp),
            backgroundColor = DLChordsTheme.colors.background,
            shape = DLChordsTheme.shapes.medium
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Letra y Acordes",
                        style = DLChordsTheme.typography.subtitle1,
                        maxLines = 1

                    )
                    Image()
                    Text(
                        text = "Disfruta de los acordes y letra del audio",
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
                            Icons.Default.Info,
                            contentDescription = "MenuAlmacenados"
                        )
                    }

                    IconButton(
                        onClick = {
                            //TODO
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "MenuAlmacenados"
                        )
                    }
                }

            }


        }
    }

}

@Composable
fun Image() {
    Image(
        painterResource(DLChordsTT.R.drawable.lyrics_chords_image),
        "Imagen",
        modifier = Modifier
            .width(160.dp)
            .height(120.dp)
    )
}


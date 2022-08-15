package com.example.dlchordstt1.features.lists_music.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dlchordstt1.ui.theme.DLChordsTheme

@Composable
@Preview(showBackground = true)
fun CartaListaAlmacenados() {
    DLChordsTheme() {
        Card(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(1f),
            backgroundColor = DLChordsTheme.colors.background,
        ) {
            Row(Modifier.padding(horizontal = 0.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.80f)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = "Audio_01_Como_La_Flor", fontSize = 16.sp, maxLines = 1)
                    Spacer(modifier = Modifier.padding(vertical = 2.dp))
                    Text(text = "03:04 min", fontSize = 14.sp)
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = {
                            //TODO
                        }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "MenuAlmacenados"
                        )
                    }
                }
            }
        }
    }

}
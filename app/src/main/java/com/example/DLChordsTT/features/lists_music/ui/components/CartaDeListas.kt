package com.example.DLChordsTT.features.lists_music.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
@Preview(showBackground = true)
fun CartaListaAlmacenados(title: String = "Titulo de Audio", duration: String = "01:11 min") {
    DLChordsTheme() {
        Card(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(1f),
            shape = RoundedCornerShape(4.dp),
            backgroundColor = DLChordsTheme.colors.cardColor,
            border = BorderStroke(1.dp,DLChordsTheme.colors.divider),
        ) {
            Row(Modifier.padding(horizontal = 0.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.80f)
                        .padding(8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = title, style = DLChordsTheme.typography.subtitle1, color = DLChordsTheme.colors.primaryText, maxLines = 1)
                    Spacer(modifier = Modifier.padding(vertical = 2.dp))
                    Text(text = duration, style = DLChordsTheme.typography.subtitle2, color = DLChordsTheme.colors.secondaryText)
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
                            tint = DLChordsTheme.colors.primary,
                            contentDescription = "MenuAlmacenados"
                        )
                    }
                }
            }
        }
    }

}
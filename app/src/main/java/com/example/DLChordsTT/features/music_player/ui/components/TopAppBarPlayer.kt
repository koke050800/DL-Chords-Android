package com.example.DLChordsTT.features.music_player.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun TopAppBarPlayer(textOnTop: String) {

    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth(0.87f)){
            Text(
                text = textOnTop,
                style = DLChordsTheme.typography.h5,
                color = DLChordsTheme.colors.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPlayerPreview() {
    DLChordsTheme {
        TopAppBarPlayer("Audio_01_Como_La_Flor")
    }
}
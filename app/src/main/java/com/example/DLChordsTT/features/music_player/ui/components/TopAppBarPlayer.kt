package com.example.DLChordsTT.features.music_player.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
@Preview(showBackground = true)
fun TopAppBar(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Audio_01_Como_La_Flor",
                        style = DLChordsTheme.typography.h5,
                        color = DLChordsTheme.colors.primary
                    )
                },
                backgroundColor = DLChordsTheme.colors.background,
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){}
}

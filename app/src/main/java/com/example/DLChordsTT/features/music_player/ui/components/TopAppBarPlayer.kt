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
@Composable
fun TopAppBarPlayer(textOnTop: String) {

    var textOnSearchBar by remember {
        mutableStateOf("")
    }

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
        Text(
            text = "Audio_01_Como_La_Flor",
            style = DLChordsTheme.typography.h5,
            color = DLChordsTheme.colors.primary
        )
        /*OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.87f)
                .height(56.dp),
            value = textOnSearchBar,
            onValueChange = { textOnSearchBar = it },
            label = { Text(text = "Buscar por nombre", style = DLChordsTheme.typography.caption) },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }, enabled = false) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Botón para buscar canciones por nombre"
                    )
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(32.dp),
        )*/
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPlayerPreview() {
    DLChordsTheme {
        TopAppBarPlayer("")
    }
}
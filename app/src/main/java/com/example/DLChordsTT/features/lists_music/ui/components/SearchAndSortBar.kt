package com.example.DLChordsTT.features.lists_music.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@Composable
fun SearchAndSortBar(textOfSearch:String ){

    var createdDate by remember {
        mutableStateOf("")
    }

    TextField(
        value = createdDate,



        onValueChange = { createdDate = it },

        label = { Text("Buscar por nombre") },
        placeholder = { Text("") },
        leadingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Botón para buscar canciones por nombre"
                )
            }
        },
        modifier = Modifier.border(
            BorderStroke(
                width = 4.dp,
                brush = Brush.horizontalGradient(listOf(DLChordsTheme.colors.primary, DLChordsTheme.colors.secondary))
            ),
            shape =  DLChordsTheme.shapes.large
        ),
    )

}

@Preview
@Composable
fun SearchAndSortBarPreview(){
    DLChordsTheme {
        SearchAndSortBar("")
    }
}
package com.example.DLChordsTT.features.lists_music.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@Composable
fun SearchAndSortBar(textOnSearchBar: String) {

    var textOnSearchBar by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {

        Box(
            modifier = Modifier
                .background(
                    DLChordsTheme.colors.cardColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .fillMaxWidth(0.87f)
                .height(60.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(1f),
                value = textOnSearchBar,
                onValueChange = { textOnSearchBar = it },
                label = {
                    Text(
                        text = "Buscar por nombre",
                        style = DLChordsTheme.typography.caption
                    )
                },
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = DLChordsTheme.colors.divider

                )

            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.SortByAlpha,
                    contentDescription = "Ordenar ascendente y descendente"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAndSortBarPreview() {
    DLChordsTheme {
        SearchAndSortBar("")
    }
}
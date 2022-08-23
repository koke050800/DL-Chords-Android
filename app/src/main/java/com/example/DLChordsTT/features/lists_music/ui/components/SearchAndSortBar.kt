package com.example.DLChordsTT.features.lists_music.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@Composable
fun SearchAndSortBar(textOnSearchBar: String) {

    var textOnSearchBar by remember {
        mutableStateOf("")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
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
        )
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
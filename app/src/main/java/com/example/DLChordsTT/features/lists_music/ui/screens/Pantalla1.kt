package com.example.DLChordsTT.features.lists_music.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.DLChordsTT.features.lists_music.navigation.Destinations
import com.example.DLChordsTT.features.lists_music.ui.components.CartaListaAlmacenados
import com.example.DLChordsTT.features.lists_music.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.lists_music.ui.components.SearchAndSortBar
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@Composable
fun Pantalla1() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        LazyColumn() {
            item { LabelAndDividerOfLists(label = "Audios Almacenados") }
            // Add 5 items
            items(12) { index ->
                CartaListaAlmacenados()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoredListPreview() {
    DLChordsTheme {
        Pantalla1()
    }
}
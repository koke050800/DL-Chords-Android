package com.example.DLChordsTT.features.lists_music.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.DLChordsTT.features.lists_music.ui.components.CartaListaAlmacenados
import com.example.DLChordsTT.features.lists_music.ui.components.LabelAndDividerOfLists
import com.example.DLChordsTT.features.lists_music.ui.components.SearchAndSortBar

@Composable
fun Pantalla2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndSortBar(textOnSearchBar = "")
        LabelAndDividerOfLists(label = "Audios Procesados")

        LazyColumn() {


            // Add 5 items
            items(10) { index ->
                CartaListaAlmacenados()
            }


        }

    }
}
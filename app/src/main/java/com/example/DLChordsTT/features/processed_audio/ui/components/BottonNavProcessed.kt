package com.example.DLChordsTT.features.processed_audio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.DLChordsTT.features.navigation.AppNavigations


@Composable
fun BottonNavProcesed(navController: NavController){
    Column(){
    Row(modifier = Modifier.height(24.dp)) {
        IconButton(
            onClick = { navController.navigate(route= AppNavigations.SecondScreen.route + "/Alamacenados") }
        ) {
            Icon(
                Icons.Default.Info,
                contentDescription = "MenuAlmacenados"
            )
        }

        IconButton(
            onClick = { navController.navigate(route= AppNavigations.SecondScreen.route + "/Procesados") }
        ) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "MenuAlmacenados"
            )
        }
    }
    }
}
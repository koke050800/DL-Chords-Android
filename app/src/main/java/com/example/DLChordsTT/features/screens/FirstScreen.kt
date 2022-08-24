package com.example.jetpackcomponavigation.screens

import android.support.v4.os.IResultReceiver
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.DLChordsTT.features.navigation.AppNavigations
import com.example.DLChordsTT.features.processed_audio.ui.components.BottonNavProcesed

@Composable
fun FirstScreen(navController: NavController){
    Scaffold(topBar = {
        TopAppBar() {
            Text(text = "FirstScreen")
        }
    }) {
    BodyComponent(navController)
    }
}

@Composable
fun BodyComponent(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hola Navegación")
        BottonNavProcesed(navController)
            Text("Navega")
        }

}


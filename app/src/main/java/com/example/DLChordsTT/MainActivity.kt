package com.example.DLChordsTT

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.DLChordsTT.features.lists_music.navigation.Destinations
import com.example.DLChordsTT.features.lists_music.ui.components.BottomNavigationBar
import com.example.DLChordsTT.features.lists_music.ui.screens.NavigationHost
import com.example.DLChordsTT.ui.theme.DLChordsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DLChordsTheme() {
                MainScreen()
            }
        }


    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        Destinations.StoredAudios,
        Destinations.Pantalla2,

    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, items = navigationItems) },
        isFloatingActionButtonDocked = false,
        floatingActionButtonPosition = FabPosition.End,
        backgroundColor = MaterialTheme.colors.background
    ){
        NavigationHost(navController)
    }
}


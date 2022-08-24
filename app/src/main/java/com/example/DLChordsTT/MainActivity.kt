package com.example.DLChordsTT

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.DLChordsTT.ui.theme.DLChordsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DLChordsTheme() {
                //NavigationHost(navController = )
            }
        }


    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        StoredAudios,
        Pantalla2,

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


package com.example.DLChordsTT

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


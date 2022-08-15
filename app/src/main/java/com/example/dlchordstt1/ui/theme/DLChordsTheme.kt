package com.example.dlchordstt1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object DLChordsTheme {
    val colors: DLChordsColors
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()){
            DLChordsDarkTheme
        }else{
            DLChordsLightTheme
        }

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = Typography(colors)

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = Shapes
}
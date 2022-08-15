package com.example.dlchordstt1.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Aplica el tema de DLChords a el contenido de este [Composable]
 * @param statusBarColor Aplica este color al StatusBar al hacer la primera composicion
 * @param content Contenido a aplicar el tema DLChords
 * @see [SystemUiController]
 * @see [MaterialTheme]
 */
@Composable
fun DLChordsTheme(
    updateStatusBar: Boolean = true,
    statusBarColor: Color? = null,
    isStatusBarColorsDark: Boolean? = null,
    content: @Composable (SystemUiController) -> Unit
) {
    MaterialTheme(
        colors = DLChordsTheme.colors.toColors(),
        typography = DLChordsTheme.typography,
        shapes = DLChordsTheme.shapes
    ){
        val uiController = rememberSystemUiController()

        if(updateStatusBar){
            val statusBarColorImpl = statusBarColor ?: DLChordsTheme.colors.background

            uiController.setStatusBarColor(
                statusBarColorImpl,
                isStatusBarColorsDark ?: (statusBarColorImpl.luminance() > 0.5f)
            )
        }

        uiController.setNavigationBarColor(DLChordsTheme.colors.toolbar)

        content(uiController)
    }
}
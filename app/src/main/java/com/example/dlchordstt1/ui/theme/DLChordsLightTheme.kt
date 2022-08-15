package com.example.dlchordstt1.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object DLChordsLightTheme : DLChordsColors {
    override val primaryText = Color(0xFF292929)
    override val secondaryText = Color(0xFF575757)
    override val hintText = Color(0xFF959595)

    override val success = Color(0xFF4CAF50)
    override val info = Color(0xFF03A9F4)
    override val warning = Color(0xFFFFB300)
    override val danger = Color(0xFFF44336)

    override val toolbar = primaryColor.s500
    override val onToolbar = Color.White

    override val divider = Color(0xFFC6C6C6)

    override val isLight = true

    override val primary = primaryColor.s700
    override val primaryVariant = primaryColor.s900
    override val onPrimary = Color.White

    override val secondary = secondaryColor.s900
    override val secondaryVariant = Color.Black
    override val onSecondary = Color.White

    override val background = Color(0xFFEDF0F3)
    override val onBackground = primaryText

    override val surface = Color(0xFFF5F2F2)
    override val onSurface = primaryText

    override val error = Color(0xFFB00020)
    override val onError = Color.White

    override fun toColors() = Colors(
        primary,
        primaryVariant,
        secondary,
        secondaryVariant,
        background,
        surface,
        error,
        onPrimary,
        onSecondary,
        onBackground,
        onSurface,
        onError,
        isLight
    )
}

package com.example.dlchordstt1.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

object DLChordsDarkTheme : DLChordsColors {
    override val primaryText = Color(0xFFFFFFFF)
    override val secondaryText = Color(0xFFB6B6B6)
    override val hintText = Color(0xFF686868)

    override val success = Color(0xFFA5D6A7)
    override val info = Color(0xFF81D4FA)
    override val warning = Color(0xFFFFE082)
    override val danger = Color(0xFFEF9A9A)

    override val toolbar = Color(0xFF272727)
    override val onToolbar = Color.White

    override val divider = Color(0xFF595959)

    override val isLight = false

    override val primary = primaryColor.s200
    override val primaryVariant = primaryColor.s500
    override val onPrimary = Color.Black

    override val secondary = secondaryColor.s200
    override val secondaryVariant = secondaryColor.s500
    override val onSecondary = Color.Black

    override val background = Color(0xFF383838)

    //override val background = Color(0xFF121212)
    override val onBackground = primaryText

    override val surface = Color(0xFF1e1e1e)
    override val onSurface = primaryText

    override val error = Color(0xFFE57373)
    override val onError = Color.Black

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
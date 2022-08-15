package com.example.DLChordsTT.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

/**
 * Matiz del color primario
 * @see ColorShade
 */
val primaryColor = object : ColorShade {
    override val s50 = Color(0xFFffeaee)
    override val s100 = Color(0xFFffcad1)
    override val s200 = Color(0xFFf59597)
    override val s300 = Color(0xFFed6a6f)
    override val s400 = Color(0xFFf8434a)
    override val s500 = Color(0xFFfe282d)
    override val s600 = Color(0xFFef192d)
    override val s700 = Color(0xFFDD0329) //este es el que se uso en los diseños
    override val s800 = Color(0xFFd00020)
    override val s900 = Color(0xFFC10021)
}

/**
 * Matiz del color secundario
 * @see ColorShade
 */
val secondaryColor = object : ColorShade {
    override val s50 = Color(0xFFf7f7f7)
    override val s100 = Color(0xFFeeeeee)
    override val s200 = Color(0xFFe2e2e2)
    override val s300 = Color(0xFFd0d0d0)
    override val s400 = Color(0xFFababab)
    override val s500 = Color(0xFF8a8a8a)
    override val s600 = Color(0xFF636363)
    override val s700 = Color(0xFF505050)
    override val s800 = Color(0xFF323232)
    override val s900 = Color(0xFF121212) //Este era el que se tenia de secundario
}

/**
 * Colores personalizados que se utilizan en la app. Es accesible por medio de la funcion [getCurrentTheme]
 */
interface DLChordsColors {
    val primary: Color
    val primaryVariant: Color
    val onPrimary: Color

    val secondary: Color
    val secondaryVariant: Color
    val onSecondary: Color

    val background: Color
    val onBackground: Color

    val surface: Color
    val onSurface: Color

    val error: Color
    val onError: Color

    val isLight: Boolean

    val primaryText: Color
    val secondaryText: Color
    val hintText: Color

    val success: Color
    val info: Color
    val warning: Color
    val danger: Color

    val toolbar: Color
    val onToolbar: Color

    val divider: Color

    fun toColors(): Colors
}

/**
 * Colores determinados por su Matiz
 */
interface ColorShade {
    val s50 : Color
    val s100 : Color
    val s200 : Color
    val s300 : Color
    val s400 : Color
    val s500 : Color
    val s600 : Color
    val s700 : Color
    val s800 : Color
    val s900 : Color
}
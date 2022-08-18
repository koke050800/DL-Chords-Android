package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlin.math.round


@Composable
@Preview(showBackground = false)
fun PlayerMusicStored() {
    Card(
            shape = DLChordsTheme.shapes.medium,
            modifier = Modifier.width(200.dp)
    ) {
        Image(
                painter = painterResource(id = R.drawable.musicplayer_image),
                "Player",
        )
    }
}
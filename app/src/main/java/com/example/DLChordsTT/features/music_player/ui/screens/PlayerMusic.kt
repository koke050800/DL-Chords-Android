package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.music_player.ui.components.TopAppBarPlayer
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlin.math.round


@Composable
@Preview(showBackground = true)
fun PlayerMusicStored() {
    DLChordsTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),

        ) {
            TopAppBarPlayer(textOnTop = "Audio_01_Como_La_Flor")
            Card(
                shape = DLChordsTheme.shapes.medium,
                modifier = Modifier
                    .width(254.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 38.dp, bottom = 25.dp),

            ) {
                Image(
                    painter = painterResource(id = R.drawable.musicplayer_image),
                    "Player",
                )
            }
            val range = 0f..100f
            var selection by remember { mutableStateOf(50f) }
            Slider(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 40.dp),
                value = selection,
                valueRange = range,
                onValueChange = { selection = it },
                colors = SliderDefaults.colors(
                    thumbColor = DLChordsTheme.colors.secondaryText,
                    activeTrackColor = DLChordsTheme.colors.secondaryText
                )
            )
            Text(
                modifier = Modifier.padding(start = 45.dp),
                textAlign = TextAlign.Left,
                text = "Opciones de procesamiento",
                style = DLChordsTheme.typography.h5,
                maxLines = 1
            )

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(vertical = 40.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(1.dp, color = DLChordsTheme.colors.divider, shape = CircleShape),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DLChordsTheme.colors.background,
                )
            ) {
                Text(
                    text = "FRAGMENTO DE AUDIO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.primary
                )
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(bottom = 40.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                contentPadding = PaddingValues(20.dp, 12.dp),

            ) {
                Text(
                    text = "AUDIO COMPLETO",
                    style = DLChordsTheme.typography.button,
                    maxLines = 1,
                    color = DLChordsTheme.colors.surface
                )
            }
        }
    }
}
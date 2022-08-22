package com.example.DLChordsTT.features.music_player.ui.screens

import DLChordsTT.R
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlin.math.round


@Composable
@Preview(showBackground = true)
fun PlayerMusicStored() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            shape = DLChordsTheme.shapes.medium,
            modifier = Modifier.width(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.musicplayer_image),
                "Player",
            )
        }
        Text(
            text = "Opciones de procesamiento",
            style = DLChordsTheme.typography.h5,
            maxLines = 1
        )
        Button(
            onClick = {},
            modifier = Modifier,
            shape = CircleShape,
            elevation = ButtonDefaults.elevation(0.dp,0.dp),
            contentPadding = PaddingValues(20.dp,12.dp),
        ){
            
        }
    }

}
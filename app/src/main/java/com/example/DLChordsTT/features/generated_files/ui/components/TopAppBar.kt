package com.example.DLChordsTT.features.music_player.ui.components

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models.AudioViewModel
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun TopAppBarPDF(textOnTop: String) {
    val activity = (LocalContext.current as? Activity)

    Row(modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp,0.dp,12.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            IconButton(onClick = {
                activity?.finish()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBackIos,
                    contentDescription = "Regresar"
                )
            }
        }
        Box(modifier = Modifier
            .align(Alignment.CenterVertically)
            .fillMaxWidth(0.87f)) {
            Text(
                text = textOnTop,
                style = DLChordsTheme.typography.h5,
                color = DLChordsTheme.colors.primary
            )
        }
    }
}
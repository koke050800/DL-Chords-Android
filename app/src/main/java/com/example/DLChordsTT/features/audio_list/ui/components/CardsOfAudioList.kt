package com.example.DLChordsTT.features.audio_list.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.ui.screens.PlayerMusicActivity
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlin.math.floor

@Composable
fun StoredCard(audio: Audio, indexAudio: Int) {

    val context = LocalContext.current
    val sendAudio = Intent(context, PlayerMusicActivity::class.java)
    sendAudio.putExtra("AudioId", indexAudio)

    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(1f)
            .clickable { startActivity(context, sendAudio, null) },
        shape = RoundedCornerShape(4.dp),
        backgroundColor = DLChordsTheme.colors.cardColor,
        border = BorderStroke(1.dp, DLChordsTheme.colors.divider),
    ) {
        Row(Modifier.padding(horizontal = 0.dp)) {
            Column(
                modifier = Modifier
                    .weight(0.80f)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = audio.displayName,
                    style = DLChordsTheme.typography.subtitle1,
                    color = DLChordsTheme.colors.primaryText,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Text(
                    text = timeStampToDuration(audio.duration.toLong()),
                    style = DLChordsTheme.typography.subtitle2,
                    color = DLChordsTheme.colors.secondaryText
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(
                    onClick = {
                        //TODO
                    }
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        tint = DLChordsTheme.colors.primary,
                        contentDescription = "MenuAlmacenados"
                    )
                }
            }
        }
    }
}


@Composable
fun ProcessedCard(audio: AudioProc) {

    Card(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = DLChordsTheme.colors.cardColor,
        border = BorderStroke(1.dp, DLChordsTheme.colors.divider),
    ) {
        Row(Modifier.padding(horizontal = 0.dp)) {
            Column(
                modifier = Modifier
                    .weight(0.80f)
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = audio.displayName,
                    style = DLChordsTheme.typography.subtitle1,
                    color = DLChordsTheme.colors.primaryText,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.padding(vertical = 2.dp))
                Text(
                    text = timeStampToDuration(audio.duration.toLong()),
                    style = DLChordsTheme.typography.subtitle2,
                    color = DLChordsTheme.colors.secondaryText
                )
            }
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(
                    onClick = {
                        //TODO
                    }
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        tint = DLChordsTheme.colors.primary,
                        contentDescription = "MenuProcesados"
                    )
                }
            }
        }
    }
}


    fun timeStampToDuration(position: Long): String {
    val totalSeconds = floor(position / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)

    return if (position < 0) {
        "--:--"
    } else {
        if (minutes < 10) "0%d:%02d".format(
            minutes,
            remainingSeconds
        ) else "%d:%02d".format(minutes, remainingSeconds)
    }
}
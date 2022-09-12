package com.example.DLChordsTT.features.audio_list.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme

@Composable
fun LabelAndDividerOfLists(label: String = "Label"){
    Row(Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            style = DLChordsTheme.typography.body2,
            color = DLChordsTheme.colors.primary,

            modifier = Modifier.padding(end = 12.dp)
        )
        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Divider(color = DLChordsTheme.colors.divider)
        }
    }
}

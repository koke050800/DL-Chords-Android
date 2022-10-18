package com.example.DLChordsTT.features.audio_list.ui.components

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.delay

@Composable
fun AlertDialogProcessedAudio(
    openDialogProcessedAudio: MutableState<Boolean>,
    expandedMenu: MutableState<Boolean>? = null
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (openDialogProcessedAudio.value) {

            AlertDialog(
                onDismissRequest = {},
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth(1.2f)
                            .padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Ya se procesó este audio, puede eliminar el resultado desde la lista de procesados.",
                            style = DLChordsTheme.typography.subtitle1
                        )
                    }
                },
                confirmButton = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(.55f)
                                .padding(bottom = 4.dp),
                            shape = CircleShape,
                            onClick = {
                                openDialogProcessedAudio.value = false
                                expandedMenu?.value = false
                            }) {
                            Text(
                                text = "Entendido",
                                style = DLChordsTheme.typography.button,
                                maxLines = 1,
                                color = DLChordsTheme.colors.onPrimary
                            )

                        }
                    }

                },
            )
        }
    }
}

@Composable
fun AlertDialogProcessing(
    openDialogProcessing: MutableState<Boolean>,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (openDialogProcessing.value) {
            Dialog(onDismissRequest = {},
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //CircularProgressIndicator()
                        Card(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Column(Modifier.padding(all = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(modifier = Modifier.padding(all = 8.dp))
                                var ticks by remember { mutableStateOf(0) }
                                LaunchedEffect(Unit) {
                                    while(true) {
                                        delay(1000)
                                        ticks++
                                    }
                                }
                                Text(
                                    "${formatDuration(ticks.toLong())}",
                                    style = DLChordsTheme.typography.caption,
                                    color = DLChordsTheme.colors.onSurface,
                                    maxLines = 1,
                                    modifier = Modifier.padding(all = 8.dp)
                                )
                            }

                        }
                    }
                }
            )
        }
    }
}

fun formatDuration(seconds: Long): String = if (seconds < 60) {
    if (seconds<10) "Tiempo 00:0$seconds" else "Tiempo 00:$seconds"
} else {
    "Tiempo ${DateUtils.formatElapsedTime(seconds)}"
}
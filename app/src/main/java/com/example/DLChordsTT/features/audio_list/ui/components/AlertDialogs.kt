package com.example.DLChordsTT.features.audio_list.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.DLChordsTT.ui.theme.DLChordsTheme

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
                        modifier = Modifier.fillMaxWidth(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            )
        }
    }
}
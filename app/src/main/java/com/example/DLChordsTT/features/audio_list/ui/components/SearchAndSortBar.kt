package com.example.DLChordsTT.features.audio_list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAndSortBar(state: MutableState<TextFieldValue>, focusManager: FocusManager, onClick: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp)
    ) {

        OutlinedTextFieldBackground(DLChordsTheme.colors.cardColor) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.87f)
                    .height(64.dp),
                value = state.value,
                onValueChange = { value ->
                    state.value = value
                },
                label = {
                    Text(
                        text = "Buscar por nombre",
                        style = DLChordsTheme.typography.caption
                    )
                },
                placeholder = { Text("") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Botón para buscar canciones por nombre"
                    )
                },
                trailingIcon = {
                    if (state.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                state.value =
                                    TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                singleLine = true,
                shape = RoundedCornerShape(32.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = DLChordsTheme.colors.divider

                )

            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.SortByAlpha,
                    contentDescription = "Ordenar ascendente y descendente"
                )
            }
        }
    }
}
@Composable
fun OutlinedTextFieldBackground(
    color: Color,
    content: @Composable () -> Unit
) {
    // This box just wraps the background and the OutlinedTextField
    Box {
        // This box works as background
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 8.dp) // adding some space to the label
                .background(
                    color,
                    // rounded corner to match with the OutlinedTextField
                    shape = RoundedCornerShape(32.dp)
                )
        )
        // OutlineTextField will be the content...
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchAndSortBarPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    DLChordsTheme {
        //SearchAndSortBar(textState)
    }
}
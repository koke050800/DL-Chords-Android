package com.example.DLChordsTT.features.ui.components

import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.ui.theme.DLChordsTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
@Preview
fun ToggleOptionSwitch(
    modifier: Modifier = Modifier,
    optionLeft: String = "option 1",
    optionRight: String = "option 2",
    switchValue: SwipeableState<SwitchOptions> = rememberSwipeableState(initialValue = SwitchOptions.OptionLeft),
){
    val density = LocalDensity.current
    val optionLeftWidth = remember {
        mutableStateOf(1.dp)
    }
    val optionRightWidth = remember {
        mutableStateOf(1.dp)
    }
    val componentWidth = remember {
        mutableStateOf(2.dp)
    }
    val verticalPadding = 8.dp
    val horizontalPadding = 16.dp

    val currentButtonWidth = when(switchValue.targetValue){
        SwitchOptions.OptionLeft -> optionLeftWidth.value
        SwitchOptions.OptionRight -> optionRightWidth.value
    }

    val sizePx = with(LocalDensity.current) { componentWidth.value.toPx() - currentButtonWidth.toPx() }

    val anchors = mapOf(
        0f to SwitchOptions.OptionLeft,
        sizePx to SwitchOptions.OptionRight
    )

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(DLChordsTheme.colors.divider)
            .onGloballyPositioned {
                with(density) {
                    componentWidth.value = it.size.width.toDp()
                }
            }.swipeable(
                state = switchValue,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.7f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Row {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .clickable {
                        scope.launch {
                            switchValue.animateTo(SwitchOptions.OptionLeft)
                        }
                    }.onGloballyPositioned {
                        with(density) {
                            optionLeftWidth.value = it.size.width.toDp()
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        ),
                    text = optionLeft.uppercase(),
                    style = DLChordsTheme.typography.button
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .clickable {
                        scope.launch {
                            switchValue.animateTo(SwitchOptions.OptionRight)
                        }
                    }.onGloballyPositioned {
                        with(density) {
                            optionRightWidth.value = it.size.width.toDp()
                        }
                    }
            ) {
                Text(
                    modifier = Modifier.padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                        ),
                    text = optionRight.uppercase(),
                    style = DLChordsTheme.typography.button
                )
            }
        }
        Box(
            modifier = Modifier
                .offset{ IntOffset(switchValue.offset.value.roundToInt(), 0) }
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(100))
                    .background(DLChordsTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = verticalPadding
                    ),
                    text = when(switchValue.targetValue){
                        SwitchOptions.OptionLeft -> optionLeft.uppercase()
                        SwitchOptions.OptionRight -> optionRight.uppercase()
                    },
                    style = DLChordsTheme.typography.button
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PassengerDriverSwitchPreview(){
    DLChordsTheme {
        ToggleOptionSwitch()
    }
}

enum class SwitchOptions{
    OptionLeft,
    OptionRight
}
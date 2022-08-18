package com.example.DLChordsTT.features.lists_music.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.DLChordsTT.features.ui.components.SwitchOptions
import com.example.DLChordsTT.features.ui.components.ToggleOptionSwitch
//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
/*
@OptIn(ExperimentalPagerApi::class, InternalCoroutinesApi::class, ExperimentalMaterialApi::class)
@Composable
fun AudioListSection(
    //passengerTravelHistoryViewModel: PassengerTravelHistoryViewModel,
    //driverTravelHistoryViewModel: DriverTravelHistoryViewModel,
) {


    val switchOption = rememberSwipeableState(initialValue = SwitchOptions.OptionLeft)
    val selectedPage = rememberPagerState(0)

    Column {
        //TransparentAppBar("Historial de viajes")
        ToggleOptionSwitch(
            modifier = Modifier
                .padding(top = 0.dp, bottom = 12.dp)
                .align(Alignment.CenterHorizontally),
            "Pasajero",
            "Conductor",
            switchValue = switchOption
        )
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = selectedPage,
            count = 2
        ) { /*page ->
            when (page) {
                0 -> //PassengerTravelHistoryList(
                   // passengerTravelHistoryViewModel = passengerTravelHistoryViewModel
                )
                1 -> //DriverTravelHistoryList(
                    //driverTravelHistoryViewModel = driverTravelHistoryViewModel
                )
            }*/
        }
    }

    LaunchedEffect(switchOption){
        snapshotFlow { switchOption.targetValue }.collect {
            kotlin.runCatching {
                if(it != switchOption.currentValue){
                    when(it){
                        SwitchOptions.OptionLeft -> if(selectedPage.currentPage != 0) selectedPage.animateScrollToPage(0)
                        SwitchOptions.OptionRight -> if(selectedPage.currentPage != 1) selectedPage.animateScrollToPage(1)
                    }
                }
            }
        }
    }
    LaunchedEffect(selectedPage) {
        snapshotFlow { selectedPage.currentPage }.collect { page ->
            kotlin.runCatching {
                switchOption.animateTo(
                    when (page) {
                        0 -> SwitchOptions.OptionLeft
                        1 -> SwitchOptions.OptionRight
                        else -> SwitchOptions.OptionLeft
                    }
                )
            }
        }
    }
}
*/
package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models


data class AudioProcessedListState(
    val isLoading: Boolean = false,
    val audioProcessedList: List<AudioProc> = emptyList(),
    val audioProcessedListInverted: List<AudioProc> = emptyList(),
    val error: String = ""
)
package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models

import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc

data class AudioProcessedListState(
    val isLoading: Boolean = false,
    val audioProcessedList: List<AudioProc> = emptyList(),
    val error: String = ""
)
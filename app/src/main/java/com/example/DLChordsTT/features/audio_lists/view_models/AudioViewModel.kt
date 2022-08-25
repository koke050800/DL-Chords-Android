package com.example.DLChordsTT.features.audio_lists.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.repositories.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository
) : ViewModel() {
    var storedAudioList = mutableStateListOf<Audio>()

    init {
        viewModelScope.launch {
            storedAudioList += getStoredAudios()
        }
    }

    private suspend fun getStoredAudios(): List<Audio> {
        return repository.getCellphoneAudioData().map {
            val displayName = it.displayName.substringBefore(".")
            val artist = if (it.artist.contains("<unknown>"))
                "Unknown Artist" else it.artist
            it.copy(
                displayName = displayName,
                artist = artist

            )
        }


    }

}
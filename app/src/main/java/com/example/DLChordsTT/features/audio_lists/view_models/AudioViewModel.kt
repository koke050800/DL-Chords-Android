package com.example.DLChordsTT.features.audio_lists.view_models

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.repositories.AudioRepository
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioRepository: AudioRepository
) : ViewModel() {
    var storedAudioList = mutableStateListOf<Audio>()
    val isRefreshing = SwipeRefreshState(false)
    val isLoading = mutableStateOf(false)

    init {
        isLoading.value = true
        getStoredAudios2()
    }

    fun getStoredAudios2() = viewModelScope.launch {
        if (!isLoading.value) {
            isRefreshing.isRefreshing = true
        }
        kotlin.runCatching {
            audioRepository.getCellphoneAudioData()
        }.onSuccess {
            storedAudioList.clear()
            storedAudioList.addAll(it.map {
                val displayName = it.displayName.substringBefore(".")
                val artist = if (it.artist.contains("<unknown>"))
                    "Unknown Artist" else it.artist
                it.copy(
                    displayName = displayName,
                    artist = artist

                )
            })
        }.onFailure { println("Falle al buscar los audios del telefono") }

        isRefreshing.isRefreshing = false
        isLoading.value = false
    }
}
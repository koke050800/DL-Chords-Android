package com.example.DLChordsTT.features.generated_files.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.repositories.AudioRepository
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.repositories.AudioProcRepository
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioProcViewModel @Inject constructor(
    private val audioprocRepository: AudioProcRepository
) : ViewModel() {
    var processedAudioList = mutableListOf<AudioProc>()
    val isRefreshing = SwipeRefreshState(false)
    val isLoading = mutableStateOf(false)

    init {
        isLoading.value = true
        getProcAudios()
    }


    fun getProcAudios() = viewModelScope.launch {
        if (!isLoading.value) {
            isRefreshing.isRefreshing = true
        }
        kotlin.runCatching {
            audioprocRepository.getDataBase()
        }.onSuccess {
            processedAudioList.clear()
            processedAudioList.addAll(it.map {

                it.copy(
                    duration = it.duration,
                    data = it.data,
                    artist = it.artist,
                    displayName = it.displayName,
                    id = it.id,
                    title = it.title,
                    id_file = it.id_file,
                )
            })
        }.onFailure { println("Falle al extraer datos de BD") }

        isRefreshing.isRefreshing = false
        isLoading.value = false

    }

}
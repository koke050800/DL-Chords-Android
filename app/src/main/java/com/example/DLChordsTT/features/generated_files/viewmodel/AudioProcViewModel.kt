package com.example.DLChordsTT.features.generated_files.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        println("Estoy en init del Proc viewmodel ${processedAudioList.size}")
        processedAudioList.forEach { println("-->${it.displayName} \n") }
    }


    fun getProcAudios() = viewModelScope.launch {
        if (!isLoading.value) {
            isRefreshing.isRefreshing = true
        }
        kotlin.runCatching {
            audioprocRepository.getDataBase()
        }.onSuccess {
            println("Tamañito--> ${it.size}")
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
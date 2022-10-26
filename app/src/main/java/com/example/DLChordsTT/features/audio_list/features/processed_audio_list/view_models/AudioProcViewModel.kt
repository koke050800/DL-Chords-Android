package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProcessedListState
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.Result
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.repositories.AudioProcRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AudioProcViewModel @Inject constructor(
    private val audioprocRepository: AudioProcRepository
) : ViewModel() {
    private val _state: MutableState<AudioProcessedListState> = mutableStateOf(
        AudioProcessedListState()
    )
    val state: State<AudioProcessedListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val isDescending = mutableStateOf(true)

    val deletedElement = mutableStateOf(false)

    init {
        getAudiosProcessedBD()
    }


    fun addNewAudioProc(audioP: AudioProc)  = viewModelScope.launch  {

        kotlin.runCatching {
            println("addNewProc: $audioP")
            audioprocRepository.addNewProcessedAudio(audioP)
        }.onSuccess {
            println("Ya agregué el rimer audio")
        }.onFailure {
            println("HUBO ERROR EN EL GENERATED FILES VIEW MODEL")
        }

    }

    fun getAudiosProcessedBD()  {
        audioprocRepository.getProcessedAudioList().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value =
                        AudioProcessedListState(error = result.message ?: "Error inesperado")
                }
                is Result.Loading -> {
                    _state.value = AudioProcessedListState(isLoading = true)
                }
                is Result.Success -> {
                    var listProcessed = emptyList<AudioProc>()
                    var sortList = emptyList<AudioProc>()

                    if (result.data?.isNotEmpty() == true) {
                        listProcessed =
                            result.data.sortedBy { it.title.lowercase(locale = Locale.getDefault()) }
                        sortList = listProcessed.reversed()
                    }

                    _state.value = AudioProcessedListState(
                        audioProcessedList = listProcessed,
                        audioProcessedListInverted = sortList
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
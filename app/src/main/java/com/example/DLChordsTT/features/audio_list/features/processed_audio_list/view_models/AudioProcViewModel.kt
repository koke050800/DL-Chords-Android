package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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


    init {
        getAudiosProcessedBD()
    }


    fun addNewAudioProc(audioP : AudioProc){

        val audio = AudioProc(
             id  = audioP.id,
         id_file = audioP.id_file,
         displayName = audioP.displayName,
         artist = audioP.artist,
         data = audioP.data,
         duration = audioP.duration,
         title = audioP.title,
        )

            audioprocRepository.addNewProcessedAudio(audio)

    }
    fun getAudiosProcessedBD() {
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
                    _state.value = AudioProcessedListState(
                        audioProcessedList = result.data ?: emptyList<AudioProc>()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}
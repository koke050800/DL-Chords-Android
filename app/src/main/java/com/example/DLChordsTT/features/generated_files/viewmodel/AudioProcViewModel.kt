package com.example.DLChordsTT.features.generated_files.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.model.AudioProcessedListState
import com.example.DLChordsTT.features.generated_files.database.model.Result
import com.example.DLChordsTT.features.generated_files.database.repositories.AudioProcRepository
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioProcViewModel @Inject constructor(
    private val audioprocRepository: AudioProcRepository
) : ViewModel() {
    private val _state: MutableState<AudioProcessedListState> = mutableStateOf(AudioProcessedListState())
    val state: State<AudioProcessedListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing




    init {
        getAudiosProcessedBD()
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
package com.example.DLChordsTT.features.generated_files.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.repositories.AudioRepository
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.repositories.AudioProcRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioProcViewModel  @Inject constructor(
private val repository: AudioProcRepository
) : ViewModel() {
    var processedAudioList = mutableListOf<AudioProc>()

    init {
        viewModelScope.launch {
            processedAudioList += getProcAudios()
        }
    }

    private suspend fun getProcAudios(): List<AudioProc> {
        println("Estoy en viewmodel y " + repository.getDataBase().size)
        var processedAudioList =  repository.getDataBase()
        println("Estoy en view con la variable y " + repository.getDataBase().size)


        return repository.getDataBase().map {
            it.copy(
                duration = it.duration,
                data = it.data,
                artist = it.artist,
                displayName = it.displayName,
                id = it.id,
                title = it.title,
                id_file =  it.id_file,
                )
        }


    }

}
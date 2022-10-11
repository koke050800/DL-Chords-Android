package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import kotlinx.coroutines.launch
import java.io.File

class FileApiViewModel(
     private val repository: FileRepository = FileRepository()
) : ViewModel() {

    val isScopeCompleted = mutableStateOf(value = false)
    var responseUploadAudio: String? = null


    fun uploadAudio(audio: Audio){
        val file = File(audio.data)

        isScopeCompleted.value = viewModelScope.launch {
            println("AQUI ANDOOOOOUUU")
            responseUploadAudio =
                repository.uploadAudioAndObtainLyricChords(file = file.absoluteFile)
            println("*-*-**-*-*- $responseUploadAudio")
        }.isCompleted
    }

    fun uploadAudioAndCut(audio: Audio, time_initial: String, time_final: String){
        val file = File(audio.data)
        viewModelScope.launch {
            repository.uploadAndCutAudioAndObtainLyricChords(file = file.absoluteFile, time_initial = time_initial, time_final= time_final)
        }
    }
}
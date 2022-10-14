package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File

class FileApiViewModel(
     private val repository: FileRepository = FileRepository()
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val vmScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val isScopeCompleted = mutableStateOf<Boolean?>(value = null)
    var responseUploadAudio = mutableStateOf<String?>(null)


    fun uploadAudio(audio: Audio) = vmScope.launch {
        isScopeCompleted.value = false
        val file = File(audio.data)
        kotlin.runCatching {
            repository.uploadAudioAndObtainLyricChords(file = file.absoluteFile)
        }.onSuccess {
            responseUploadAudio.value = it
            isScopeCompleted.value = true
            println("***---->> $it")
        }.onFailure {
            println("Hubo un error al subir el audio $it")
        }
    }

    fun uploadAudioAndCut(audio: Audio, time_initial: String, time_final: String)= vmScope.launch {
        isScopeCompleted.value = false
        val file = File(audio.data)
        kotlin.runCatching {
            repository.uploadAndCutAudioAndObtainLyricChords(file = file.absoluteFile, time_initial = time_initial, time_final= time_final)
        }.onSuccess {
            responseUploadAudio.value = it
            isScopeCompleted.value = true
            println("***---->> $it")
        }.onFailure {
            println("Hubo un error al subir el audio $it")
        }
    }
}
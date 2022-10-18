package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.repositories.PythonFlaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PythonFlaskApiViewModel @Inject constructor(
    private val repository: PythonFlaskRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val vmScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val isScopeCompleted = mutableStateOf<Boolean?>(value = null)
    var responseUploadAudio = mutableStateOf<String?>(value = null)
    //val haveErrorInPrediction = mutableStateOf<Boolean?>(value = null) TODO: Manejar errores onFailure


    fun uploadAudio(audio: Audio) = vmScope.launch {
        isScopeCompleted.value = false
        val file = File(audio.data)
        kotlin.runCatching {
            repository.uploadAudioAndObtainLyricChords(file = file.absoluteFile)
        }.onSuccess {

            responseUploadAudio.value = it
            isScopeCompleted.value = true

            delay(100) //importante el delay y el null para que se reinicie al valor original
            isScopeCompleted.value = null

        }.onFailure {
            isScopeCompleted.value = true
            //haveErrorInPrediction.value = true

            delay(100)
            isScopeCompleted.value = null
            // haveErrorInPrediction.value = null
            println("Hubo un error al subir el audio $it")
        }
    }

    fun uploadAudioAndCut(audio: Audio, time_initial: String, time_final: String) = vmScope.launch {
        isScopeCompleted.value = false
        //haveErrorInPrediction.value = false
        val file = File(audio.data)
        kotlin.runCatching {
            repository.uploadAndCutAudioAndObtainLyricChords(
                file = file.absoluteFile,
                time_initial = time_initial,
                time_final = time_final
            )
        }.onSuccess {

            responseUploadAudio.value = it
            isScopeCompleted.value = true

            delay(100) //importante el delay y el null para que se reinicie al valor original
            isScopeCompleted.value = null

        }.onFailure {
            isScopeCompleted.value = true
            //haveErrorInPrediction.value = true

            delay(100)
            isScopeCompleted.value = null
            // haveErrorInPrediction.value = null
            println("Hubo un error al subir el audio $it")
        }
    }
}
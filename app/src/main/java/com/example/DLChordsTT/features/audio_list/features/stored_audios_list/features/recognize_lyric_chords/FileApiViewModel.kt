package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import kotlinx.coroutines.launch
import java.io.File

class FileApiViewModel(
     private val repository: FileRepository = FileRepository()
) : ViewModel() {
    fun uploadAudio(audio: Audio){
        val file = File(audio.data)
        viewModelScope.launch {
            repository.uploadAudioAndObtainLyricChords(file = file.absoluteFile, audioName = audio.title)
        }
    }
}
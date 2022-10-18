package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProcessedListState
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.Result
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.repositories.AudioProcRepository
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.view_models.AudioProcViewModel
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Chord
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Files
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Word
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories.GeneratedFilesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GeneratedFilesViewModel @Inject constructor(
    private val generatedFilesRepository: GeneratedFilesRepository,
) : ViewModel() {
    private val _state: MutableState<AudioProcessedListState> = mutableStateOf(AudioProcessedListState())
    val state: State<AudioProcessedListState> = _state
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    val isDescending = mutableStateOf(true)
    val deletedElement = mutableStateOf(false)

    fun generatePDFs(context: Context, id:Long,displayName:String,artist:String,data:String, duration:Int, title:String,english_nomenclature:String,latin_nomenclature:String,chords_lyrics_e:String, chords_lyrics_l:String,lyrics:String, ChordsWordsJson : String) {
        generatedFilesRepository.generatePDFs(context = context, id=id,displayName=displayName,artist=artist,data=data,
               duration=duration, title=title,english_nomenclature=english_nomenclature,latin_nomenclature=latin_nomenclature,
            chords_lyrics_e=chords_lyrics_e, chords_lyrics_l=chords_lyrics_l,lyrics=lyrics, ChordsWordsJson = ChordsWordsJson)
    }
    fun deletePDF(audioProc: AudioProc) {
        generatedFilesRepository.deleteData(audioProc = audioProc)
    }
    fun showPDF(url:String, context: Context) {
        generatedFilesRepository.showData(url= url, context = context )
    }
    fun toCardScreen(context: Context, toScreenPDF : Intent){
        generatedFilesRepository.startCardScreen(context,toScreenPDF)
    }
    fun downloadPDF(url:String, context: Context) {
        generatedFilesRepository.downloadData(url= url, context = context )

    }
}

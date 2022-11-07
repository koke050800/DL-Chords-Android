package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.*
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories.GeneratedFilesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class GeneratedFilesViewModel @Inject constructor(
    private val generatedFilesRepository: GeneratedFilesRepository,
) : ViewModel() {

    //val isCreationCompletedOfPDFs = mutableStateOf<Boolean>(value = false)
    var audiosProc = mutableStateListOf<AudioProc>()
    val isUploadingPDFsOnDB = mutableStateOf<Boolean>(value = true)
    val errorUploadPDFsOnDB = mutableStateOf<Boolean>(value = false)
    var listPDF = emptyList<File>()


    fun generatePDFs(
        context: Context,
        id: Long,
        displayName: String,
        artist: String,
        data: String,
        duration: Int,
        title: String,
        english_nomenclature: String,
        latin_nomenclature: String,
        chords_lyrics_e: String,
        chords_lyrics_l: String,
        lyrics: String,
        ChordsWordsJson: String
    ) {
       // isCreationCompletedOfPDFs.value = false

        listPDF = generatedFilesRepository.generatePDFs(
            context = context,
            id = id,
            displayName = displayName,
            artist = artist,
            data = data,
            duration = duration,
            title = title,
            english_nomenclature = english_nomenclature,
            latin_nomenclature = latin_nomenclature,
            chords_lyrics_e = chords_lyrics_e,
            chords_lyrics_l = chords_lyrics_l,
            lyrics = lyrics,
            ChordsWordsJson = ChordsWordsJson
        )
        //isCreationCompletedOfPDFs.value = true
    }

    fun UploadPDFsInBD(
        id: Long,
        displayName: String,
        artist: String,
        data: String,
        duration: Int,
        title: String,
        english_nomenclature: String,
        latin_nomenclature: String,
        chords_lyrics_e: String,
        chords_lyrics_l: String,
        lyrics: String,
        listPDF: List<File>
    ) = viewModelScope.launch {

        var audio = AudioProc(
            id = id,
            displayName = displayName,
            artist = artist,
            data = data,
            duration = duration,
            title = title,
            english_nomenclature = english_nomenclature,
            latin_nomenclature = latin_nomenclature,
            chords_lyrics_e = chords_lyrics_e,
            chords_lyrics_l = chords_lyrics_l,
            lyrics = lyrics,
        )
        var listPre =
            mutableListOf<String>("Lyrics", "ChordsE", "ChordsL", "LyricChordE", "LyricChordL")
        var cont = 0


        for (item in listPDF) {
            kotlin.runCatching {
              generatedFilesRepository.addNewGeneratedFiles(
                    audio = audio,
                    item.toUri(),
                    listPre[cont],
                    item
                )
            }.onSuccess {
                audiosProc.add(it)
                generatedFilesRepository.uploadtoFirebase(it)

            }.onFailure {
                errorUploadPDFsOnDB.value = true
            }
            cont++
        }


        isUploadingPDFsOnDB.value = false
        println("isUploadingPDFsOnDB.value ACABE >>>>>> ${isUploadingPDFsOnDB.value} ")

    }

    fun deletePDF(audioProc: AudioProc) {
        generatedFilesRepository.deleteData(audioProc = audioProc)
    }

    fun showPDF(url: String, context: Context) {
        generatedFilesRepository.showData(url = url, context = context)
    }

    fun toCardScreen(context: Context, toScreenPDF: Intent) {
        generatedFilesRepository.startCardScreen(context, toScreenPDF)
    }

    fun downloadPDF(url: String, context: Context) {
        generatedFilesRepository.downloadData(url = url, context = context)

    }
}



package com.example.DLChordsTT.features.generated_files.database.repositories

import android.content.ContentValues
import android.util.Log
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.models.ContentResolverHelper
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.model.ContentResolverHelperProc
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioProcRepository  @Inject
    constructor(private val contentResolverHelperProc: ContentResolverHelperProc) {
        suspend fun getDataBase():List<AudioProc> = withContext(Dispatchers.IO){
            println("Lista en el repo....${contentResolverHelperProc.getDataBD().size}")
            contentResolverHelperProc.getDataBD()
        }
    }

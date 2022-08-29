package com.example.DLChordsTT.features.generated_files.database.repositories

import android.content.ContentValues
import android.util.Log
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.models.ContentResolverHelper
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.model.ContentResolverHelperProc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioProcRepository  @Inject
    constructor(private val contentResolverHelperProc: ContentResolverHelperProc) {
    fun hi(){println("Estoy en audiorepositoryyyy "+ contentResolverHelperProc.getBDAudioData2().size)}

    fun hi2(){println("Estoy en audiorepositoryyyyyyyyyy "+ contentResolverHelperProc.getBDAudioData2().size)}

        suspend fun getDataBase():List<AudioProc> = withContext(Dispatchers.IO){
            hi()

            contentResolverHelperProc.getBDAudioData2()


        }

    }

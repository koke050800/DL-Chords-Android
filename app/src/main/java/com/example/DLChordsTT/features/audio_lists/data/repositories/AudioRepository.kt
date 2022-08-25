package com.example.DLChordsTT.features.audio_lists.data.repositories

import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.models.ContentResolverHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject
constructor(private val contentResolverHelper: ContentResolverHelper) {
    suspend fun getCellphoneAudioData():List<Audio> = withContext(Dispatchers.IO){
        contentResolverHelper.getCellphoneAudioData()
    }
}
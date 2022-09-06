package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.repositories.ContentResolverHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioRepository @Inject
constructor(private val contentResolverHelper: ContentResolverHelper) {
    suspend fun getCellphoneAudioData(isDescendingSort: MutableState<Boolean>):List<Audio> = withContext(Dispatchers.IO){
        contentResolverHelper.getCellphoneAudioData(isDescendingSort)
    }
}
package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models.Audio
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.models.K
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.models.exoplayer.MediaPlayerServiceConnection
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.ui.screens.AudioRepository
import com.example.DLChordsTT.features.music_player.ui.exoplayer.currentPosition
import com.example.DLChordsTT.features.music_player.ui.exoplayer.isPlaying
import com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.music_player.models.MusicPlayerService
import com.google.accompanist.swiperefresh.SwipeRefreshState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    serviceConnection: MediaPlayerServiceConnection
) : ViewModel() {
    var storedAudioList = mutableStateListOf<Audio>()

    val isRefreshing = SwipeRefreshState(false)
    val isLoading = mutableStateOf(false)
    val isLoadingStoredList = mutableStateOf(false)
    val isPlaying = mutableStateOf(false)
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true
    private val playbackState = serviceConnection.plaBackState
    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true

    var isAscending = mutableStateOf(true)

    private val subscriptionCallback = object
        : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    private val serviceConnection = serviceConnection.also {
        println("Hola ya entre aquí")
        updatePlayBack()
    }

    val currentDuration: Long
        get() = MusicPlayerService.currentDuration

    var currentAudioProgress = mutableStateOf(0f)

    init {
        isLoading.value = true
        isLoadingStoredList.value = true
        getStoredAudios()
    }


    fun changeOrderOfStoredAudioList() {
        if (storedAudioList.isNotEmpty()) {
            val sortList =  storedAudioList.reversed()
            storedAudioList.clear()
            storedAudioList.addAll(sortList)
        }
    }


    fun getStoredAudios() = viewModelScope.launch {

        if (!isLoadingStoredList.value) {
            isRefreshing.isRefreshing = true
        }
        kotlin.runCatching {
            audioRepository.getCellphoneAudioData()
        }.onSuccess {
            storedAudioList.clear()
            storedAudioList.addAll(it.map {
                val displayName = it.displayName.substringBefore(".")
                val artist = if (it.artist.contains("<unknown>"))
                    "Unknown Artist" else it.artist
                it.copy(
                    displayName = displayName,
                    artist = artist

                )
            })

            if (storedAudioList.isNotEmpty()) {
                var sortList = mutableStateListOf<Audio>()

                if (isAscending.value) {
                    sortList.addAll(storedAudioList.sortedBy { it.title.lowercase(Locale.getDefault()) })
                } else {
                    sortList.addAll(storedAudioList.sortedByDescending { it.title.lowercase(Locale.getDefault()) })
                }
                storedAudioList.clear()
                storedAudioList.addAll(sortList)
            }



            isConnected.collect {
                if (it) {
                    println("EL COLLECT")
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.plaBackState.value?.apply {
                        currentPlayBackPosition = position
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)
                    isLoading.value = false
                }

            }
        }.onFailure { println("Falle al buscar los audios del telefono") }

        isRefreshing.isRefreshing = false
        isLoadingStoredList.value = false
    }

    fun playAudio(currentAudio: Audio) {
        isPlaying.value = true
        serviceConnection.playAudio(storedAudioList)
        if (currentAudio.id == currentPlayingAudio.value?.id) {
            println("Hola neni")
            if (isAudioPlaying) {
                println("Aqui ando pausando")
                serviceConnection.transportControl.pause()
            } else {
                serviceConnection.transportControl.play()
            }

        } else {
            println("Hola zorco")
            serviceConnection.transportControl
                .playFromMediaId(
                    currentAudio.id.toString(),
                    null
                )
        }


    }

    private fun updatePlayBack() {

        viewModelScope.launch {
            val position = playbackState.value?.currentPosition ?: 0

            if (currentPlayBackPosition != position) {
                currentPlayBackPosition = position
            }

            if (currentDuration > 0) {

                try {
                    currentAudioProgress.value = (
                            currentPlayBackPosition.toFloat()
                                    / currentDuration.toFloat() * 100f

                            )
                } catch (e: Exception) {
                    println(e.message)
                }
            }

            delay(K.PLAYBACK_UPDATE_INTERVAL)

            if (updatePosition) {
                updatePlayBack()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unSubscribe(
            K.MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {}
        )
        updatePosition = false
    }

}
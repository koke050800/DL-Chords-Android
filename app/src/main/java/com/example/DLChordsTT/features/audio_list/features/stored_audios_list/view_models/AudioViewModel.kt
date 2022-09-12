package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.view_models

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import java.lang.Thread.sleep
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
    var lastAudio by mutableStateOf(0L)
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlayBackPosition by mutableStateOf(0L)
    private var updatePosition = true
    private val playbackState = serviceConnection.plaBackState

    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true

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
        getStoredAudios2()
        //conectToMediaPlayerService()


    }


    fun conectToMediaPlayerService() = viewModelScope.launch {

    }


    fun getStoredAudios2() = viewModelScope.launch {
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
        var audioList = mutableStateListOf<Audio>()
        serviceConnection.playAudio(storedAudioList)
        //serviceConnection.transportControl.
        println("CURRENT AUDIO -------- $currentAudio")
        if (currentAudio.id == currentPlayingAudio.value?.id) {
            println("Hola neni")
            if (isAudioPlaying) {
                println("Aqui ando pausando")
                serviceConnection.transportControl.stop()
            } else {
                serviceConnection.transportControl.play()
            }

        } else {
            println("Hola zorco -------////////  ${currentAudio.id}")
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
            println("Duración del audio $currentDuration")
            println("Duración del audio viejo ${playbackState.value?.state}")

            if (currentDuration > 0) {
                if (lastAudio != currentDuration) {
                    if (isAudioPlaying && (lastAudio > 80)) {
                        
                        currentPlayingAudio.value?.let { playAudio(it) }
                    }
                    lastAudio = currentDuration
                } else {

                }
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

    fun refreshMediaService() {
        onCleared()
    }

}
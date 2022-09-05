package com.example.DLChordsTT.features.audio_lists.view_models

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.repositories.AudioRepository
import com.example.DLChordsTT.features.music_player.ui.constants.K
import com.example.DLChordsTT.features.music_player.ui.exoplayer.MediaPlayerServiceConnection
import com.example.DLChordsTT.features.music_player.ui.exoplayer.currentPosition
import com.example.DLChordsTT.features.music_player.ui.exoplayer.isPlaying
import com.example.DLChordsTT.features.music_player.ui.service.MusicPlayerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import java.lang.Exception

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository,
    serviceConnection: MediaPlayerServiceConnection
) : ViewModel() {
    var storedAudioList = mutableStateListOf<Audio>()
    val isLoading = mutableStateOf(false)
    val isPlaying = mutableStateOf(false)
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
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
        viewModelScope.launch {
            storedAudioList += getStoredAudios()
            println("EL INIT DE SHIT ${isConnected.value}")
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
        }
    }

    private suspend fun getStoredAudios(): List<Audio> {
        return repository.getCellphoneAudioData().map {
            val displayName = it.displayName.substringBefore(".")
            val artist = if (it.artist.contains("<unknown>"))
                "Unknown Artist" else it.artist
            it.copy(
                displayName = displayName,
                artist = artist

            )
        }


    }

    fun playAudio(currentAudio: Audio) {
        //println("Hola qlo ${serviceConnection.isConnected}")
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

    fun pause(currentAudio: Audio) {

    }
    /*fun playAudio2(idAudio: Long) {
        serviceConnection.playAudio(storedAudioList)

            serviceConnection.transportControl
                .playFromMediaId(
                    idAudio.toString(),
                    null)
    }*/

    fun stopPlayBack() {
        serviceConnection.transportControl.stop()
    }

    fun fastForward() {
        serviceConnection.fastForward()
    }

    fun rewind() {
        serviceConnection.rewind()
    }

    fun skipToNext() {
        serviceConnection.skipToNext()
    }

    fun seekTo(value: Float) {
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong()
        )
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
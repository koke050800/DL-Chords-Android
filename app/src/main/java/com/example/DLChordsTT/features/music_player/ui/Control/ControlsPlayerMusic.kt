package com.example.DLChordsTT.features.music_player.ui.Control

import android.media.AudioManager
import android.media.MediaPlayer

fun ControlsPlayerMusic( action : String,  filename : String){
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    mediaPlayer.setDataSource(filename);
    mediaPlayer.prepare();
mediaPlayer.start()
}






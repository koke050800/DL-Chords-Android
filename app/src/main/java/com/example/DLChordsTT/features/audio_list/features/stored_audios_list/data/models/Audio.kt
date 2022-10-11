package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.data.models

import android.net.Uri

data class Audio(
    val uri: Uri ,
    val displayName:String = "",
    val id: Long = 0,
    val artist:String = "",
    val data:String = "",
    val duration:Int = 0,
    val title:String = "",
)
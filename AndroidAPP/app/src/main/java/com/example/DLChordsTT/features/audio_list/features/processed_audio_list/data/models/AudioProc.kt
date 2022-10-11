package com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models

import android.net.Uri
import java.io.Serializable

data class AudioProc (

    val id: Long = 0,
    val displayName:String = "",
    val artist:String = "",
    val data:String = "",
    val duration:Int = 0,
    val title:String = "",
    var english_nomenclature:String = "",
    var latin_nomenclature:String = "",
    var chords_lyrics_e:String = "",
    var chords_lyrics_l:String = "",
    var lyrics :String =""
): Serializable


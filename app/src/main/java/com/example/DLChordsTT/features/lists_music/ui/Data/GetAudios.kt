package com.example.DLChordsTT.features.lists_music.ui.Data

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import java.io.File

class GetAudios : AppCompatActivity( ) {
    var MusicListMA: ArrayList<Music> = getAllAudio()

}

@SuppressLint("Range")
private fun getAllAudio(): ArrayList<Music> {
    val templist = ArrayList<Music>()
    val selection = MediaStore.Audio.Media.IS_MUSIC + " !=0"
    val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.DURATION,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.DATA
    )
    val context : Context
    val cursor = applicationContext.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection,
        null, MediaStore.Audio.Media.DATE_ADDED + " DESC", null
    )
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            do {
                val titleC =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val albumC =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artistC =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val durationC =
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val music = Music(idC, titleC, albumC, artistC, durationC, pathC)

                val file = File(music.path)
                if (file.exists() && music.path.contains("/storage/emulated/0/Music")) {
                    if (music.path.substring(music.path.length - 4).equals(".mp3")
                        || (music.path.substring(music.path.length - 5).equals(".flac"))){
                        templist.add(music)
                    }
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
    return templist
}
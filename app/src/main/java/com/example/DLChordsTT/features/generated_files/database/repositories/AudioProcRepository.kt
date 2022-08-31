package com.example.DLChordsTT.features.generated_files.database.repositories

import android.content.ContentValues
import android.util.Log
import com.example.DLChordsTT.features.audio_lists.data.models.Audio
import com.example.DLChordsTT.features.audio_lists.data.models.ContentResolverHelper
import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.model.ContentResolverHelperProc
import com.example.DLChordsTT.features.generated_files.database.model.ResponseFirebaseObject
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AudioProcRepository @Inject
constructor(
   // private val contentResolverHelperProc: ContentResolverHelperProc,
    val productRef: DatabaseReference
) {
   /* suspend fun getDataBase(): List<AudioProc> = withContext(Dispatchers.IO) {
        println("Lista en el repo....${contentResolverHelperProc.getDataBD().size}")
        contentResolverHelperProc.getDataBD()
    }*/


    suspend fun getResponseFromRealtimeDatabaseUsingCoroutines(): ResponseFirebaseObject {
        val response = ResponseFirebaseObject()
        try {
            response.products = productRef.get().await().children.map { snapShot ->
                snapShot.getValue(AudioProc::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }
}

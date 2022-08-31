package com.example.DLChordsTT.features.generated_files.database.repositories

import com.example.DLChordsTT.features.generated_files.database.model.AudioProc
import com.example.DLChordsTT.features.generated_files.database.model.Result
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AudioProcRepository
@Inject
constructor(
    private val processedAudioList: CollectionReference
) {
    fun getProcessedAudioList(): Flow<Result<List<AudioProc>>> = flow {
        try {
            emit(Result.Loading<List<AudioProc>>())

            val processedAudioList = processedAudioList.get().await().map{ audioOfDB ->
                audioOfDB.toObject(AudioProc::class.java)

            }
            println("\n\n  ------ ${processedAudioList.get(0).artist}    \n\n")
            emit(Result.Success<List<AudioProc>>(data = processedAudioList))
        }catch (e: Exception){
            emit(Result.Error<List<AudioProc>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}

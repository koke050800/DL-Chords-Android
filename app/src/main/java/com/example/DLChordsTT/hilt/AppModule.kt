package com.example.DLChordsTT.hilt

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()


    @Provides
    @Singleton
    fun provideAudioProcessedList(firestore: FirebaseFirestore) = firestore.collection("AudioProc")



}
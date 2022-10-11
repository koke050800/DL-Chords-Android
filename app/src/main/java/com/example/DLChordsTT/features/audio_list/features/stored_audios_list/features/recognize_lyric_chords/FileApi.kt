package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

private const val URL = "http://192.168.137.1/"

var client = OkHttpClient.Builder().connectTimeout(720, TimeUnit.SECONDS)
    .readTimeout(720, TimeUnit.SECONDS).build();


interface FileApi {

    @Multipart
    @POST("predict")
    suspend fun uploadAudio(
        @Part audioToConvert: MultipartBody.Part
    ):Response<ResponseBody>

    @Multipart
    @POST("cut-and-predict")
    suspend fun uploadAudioAndCut(
        @Header("time_initial") time_initial: String,
        @Header("time_final") time_final: String,
        @Part audioToConvert: MultipartBody.Part

    ):Response<ResponseBody>

    // @Header("Authorization") MultipartBody.Part token

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .build()
                .create(FileApi::class.java)
        }
    }

}






package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords.models


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

private const val URL = "http://192.168.137.1/"

var client = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(420, TimeUnit.SECONDS).build();


interface PythonFlaskApi {

    @Multipart
    @POST("predict")
    fun uploadAudio(
        @Part audioToConvert: MultipartBody.Part
    ): Deferred<Response<ResponseBody>>

    @Multipart
    @POST("cut-and-predict")
    fun uploadAudioAndCut(
        @Part audioToConvert: MultipartBody.Part,
        @Header("time_initial") time_initial: String,
        @Header("time_final") time_final: String
    ): Deferred<Response<ResponseBody>>


    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(client)
                .build()
                .create(PythonFlaskApi::class.java)
        }
    }

}






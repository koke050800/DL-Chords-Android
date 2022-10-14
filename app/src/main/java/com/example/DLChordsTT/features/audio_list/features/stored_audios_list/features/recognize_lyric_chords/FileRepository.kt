package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileRepository {

    suspend fun uploadAudioAndObtainLyricChords(file: File): String? {

        val response = FileApi.instance.uploadAudio(
            audioToConvert = MultipartBody.Part.createFormData(
                name = "file",
                file.name,
                file.asRequestBody()
            )
        ).await()

        println("RESPONSE REPO uploadAudioAndObtainLyricChords:::: ${response.raw()}")

        if (response.isSuccessful) {

            // Convert raw JSON to pretty JSON using GSON library
            val gson = GsonBuilder().setPrettyPrinting().create()
            val prettyJson = gson.toJson(
                JsonParser.parseString(
                    response.body()
                        ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                )
            )

            return prettyJson.replace(Regex("""[\\]"""), "").replace("\"[", "[")
                .replace("]\"", "]")

        } else {
            return response.code().toString()
        }

    }

    suspend fun uploadAndCutAudioAndObtainLyricChords(
        file: File,
        time_initial: String,
        time_final: String
    ): String? {


        val response = FileApi.instance.uploadAudioAndCut(
            time_initial = time_initial,
            time_final = time_final,
            audioToConvert = MultipartBody.Part.createFormData(
                name = "file",
                file.name,
                file.asRequestBody()
            )
        ).await()

        println("RESPONSE REPO uploadAndCutAudioAndObtainLyricChords:::: ${response.raw()}")

        if (response.isSuccessful) {

            // Convert raw JSON to pretty JSON using GSON library
            val gson = GsonBuilder().setPrettyPrinting().create()
            val prettyJson = gson.toJson(
                JsonParser.parseString(
                    response.body()
                        ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                )
            )

            return prettyJson.replace(Regex("""[\\]"""), "").replace("\"[", "[")
                .replace("]\"", "]")

        } else {
            return response.code().toString()
        }


    }

}
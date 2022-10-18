package com.example.DLChordsTT.features.audio_list.features.stored_audios_list.features.recognize_lyric_chords

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

class FileRepository {

    suspend fun uploadAudioAndObtainLyricChords(file: File): String? {

            val response = FileApi.instance.uploadAudio(
                audioToConvert = MultipartBody.Part.createFormData(
                    name = "file",
                    file.name,
                    file.asRequestBody()
                )
            ).await()

            println("RESPONSE REPO:::: ${response.raw()}")

            if (response.isSuccessful) {

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                println("RESPONSEEEEEEEE ${response.body()}")
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                    )
                )

                return prettyJson

            } else {
                return response.code().toString()
            }

    }

    suspend fun uploadAndCutAudioAndObtainLyricChords(file: File, time_initial: String, time_final: String) {
        return try {


            val response = FileApi.instance.uploadAudioAndCut(
                time_initial = time_initial,
                time_final = time_final,
                audioToConvert = MultipartBody.Part.createFormData(
                    name = "file",
                    file.name,
                    file.asRequestBody()
                )
            )

            if (response.isSuccessful) {

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                println("reponseeeeeee${response.body()?.string()}")
                val prettyJson = gson.toJson(
                    JsonParser.parseString(
                        response.body()
                            ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                    )
                )

                println("----**-*-*-*-*-** "+prettyJson)

            } else {

                println("----**-*-*-*-*-** "+response.code().toString())

            }

        } catch (ex: IOException) {
            ex.printStackTrace()
            println(">>>>>>>>>>>>>>>   Error al leer el FILE")

        } catch (e: HttpException) {
            e.printStackTrace()
            println(">>>>>>>>>>>>>>>   Error HTTP")

        }
    }

}
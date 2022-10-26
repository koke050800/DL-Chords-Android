package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.text.TextPaint
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.Result
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Chord
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Word
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class GeneratedFilesRepository
@Inject
constructor(
    private val processedAudioList: CollectionReference
) {
    var audioFinal = AudioProc(
        id = 0,
        displayName = "displayName",
        artist = "artist",
        data = "data",
        duration = 0,
        title = "title",
        english_nomenclature = "english_nomenclature",
        latin_nomenclature = "latin_nomenclature",
        chords_lyrics_e = "chords_lyrics_e",
        chords_lyrics_l = "chords_lyrics_l",
        lyrics = "lyrics"
    )

    suspend fun addNewGeneratedFiles(
        audio: AudioProc,
        mUri: Uri,
        pre: String,
        file: File
    ): AudioProc {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child(audio.title)
        val path = mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/') + 1))
        var audioP = audio


        fileName.putFile(mUri).await()
        val downloadURl = fileName.downloadUrl.await()


        audioP = audio
        println("AUDIO REPO:::: $audioP")
        when (pre) {
            "Lyrics" -> {
                audioP.lyrics = "${downloadURl}"
                audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                audioP.english_nomenclature = "${audio.english_nomenclature}"
                audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                audioP.latin_nomenclature = "${audio.latin_nomenclature}"
            }
            "LyricChordE" -> {
                audioP.chords_lyrics_e = "${downloadURl}"
                audioP.lyrics = "${audio.lyrics}"
                audioP.english_nomenclature = "${audio.english_nomenclature}"
                audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                audioP.latin_nomenclature = "${audio.latin_nomenclature}"
            }
            "ChordsE" -> {
                audioP.english_nomenclature = "${downloadURl}"
                audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                audioP.lyrics = "${audio.lyrics}"
                audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                audioP.latin_nomenclature = "${audio.latin_nomenclature}"
            }
            "LyricChordL" -> {
                audioP.chords_lyrics_l = "${downloadURl}"
                audioP.english_nomenclature = "${audio.english_nomenclature}"
                audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                audioP.lyrics = "${audio.lyrics}"
                audioP.latin_nomenclature = "${audio.latin_nomenclature}"
            }
            "ChordsL" -> {
                audioP.latin_nomenclature = "${downloadURl}"
                audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                audioP.english_nomenclature = "${audio.english_nomenclature}"
                audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                audioP.lyrics = "${audio.lyrics}"
            }
            else -> {
                audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                audioP.english_nomenclature = "${audio.english_nomenclature}"
                audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                audioP.lyrics = "${audio.lyrics}"
            }
        }
        audioFinal = audioP
        println("Entre aqui 7 : $audioFinal")
        return audioFinal
    }

    suspend fun uploadtoFirebase(audioP: AudioProc){
        try {
            if(audioP.lyrics.isNotEmpty()){
            processedAudioList.document("${audioP.id}").set(audioP).await()
                println("Subí el archivo")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getJsonDataFramAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getListModelChordEn(context: Context, chordsJson: String): MutableList<Chord> {
        val string = getJsonDataFramAsset(context, "quien_de_los_dos_sera.json")
        val listChords = mutableListOf<Chord>()
        println(chordsJson)
        val obj = JSONObject(chordsJson)
        val jsonArray = obj.getJSONArray("chords")
        for (i in 0 until jsonArray.length()) {
            val chord = Gson().fromJson(
                jsonArray.getJSONObject(i).toString(),
                Chord::class.java
            )
            when (chord.chord_result) {
                "a" -> {
                    chord.chord_result = "A"
                }
                "am" -> {
                    chord.chord_result = "Am"
                }
                "b" -> {
                    chord.chord_result = "B"
                }
                "bm" -> {
                    chord.chord_result = "Bm"
                }
                "c" -> {
                    chord.chord_result = "C"
                }
                "cm" -> {
                    chord.chord_result = "Cm"
                }
                "d" -> {
                    chord.chord_result = "D"
                }
                "dm" -> {
                    chord.chord_result = "Dm"
                }
                "e" -> {
                    chord.chord_result = "E"
                }
                "em" -> {
                    chord.chord_result = "Em"
                }
                "f" -> {
                    chord.chord_result = "F"
                }
                "fm" -> {
                    chord.chord_result = "Fm"
                }
                "g" -> {
                    chord.chord_result = "G"
                }
                "gm" -> {
                    chord.chord_result = "Gm"
                }

            }
            listChords.add(chord)
        }
        return listChords
    }

    fun getListModelChordLat(context: Context, chordsJson: String): MutableList<Chord> {
        val string = getJsonDataFramAsset(context, "quien_de_los_dos_sera.json")
        val listChords = mutableListOf<Chord>()
        val obj = JSONObject(chordsJson)

        val jsonArray = obj.getJSONArray("chords")

        for (i in 0 until jsonArray.length()) {
            val chord = Gson().fromJson(
                jsonArray.getJSONObject(i).toString(),
                Chord::class.java
            )
            when (chord.chord_result) {
                "a" -> {
                    chord.chord_result = "La"
                }
                "am" -> {
                    chord.chord_result = "Lam"
                }
                "b" -> {
                    chord.chord_result = "Si"
                }
                "bm" -> {
                    chord.chord_result = "Sim"
                }
                "c" -> {
                    chord.chord_result = "Do"
                }
                "cm" -> {
                    chord.chord_result = "Dom"
                }
                "d" -> {
                    chord.chord_result = "Re"
                }
                "dm" -> {
                    chord.chord_result = "Rem"
                }
                "e" -> {
                    chord.chord_result = "Mi"
                }
                "em" -> {
                    chord.chord_result = "Mim"
                }
                "f" -> {
                    chord.chord_result = "Fa"
                }
                "fm" -> {
                    chord.chord_result = "Fam"
                }
                "g" -> {
                    chord.chord_result = "Sol"
                }
                "gm" -> {
                    chord.chord_result = "Solm"
                }

            }
            listChords.add(chord)

        }
        return listChords
    }

    fun getListModelWord(context: Context, wordsJson: String): MutableList<Word> {
        val string = getJsonDataFramAsset(context, "quien_de_los_dos_sera.json")
        val listWords = mutableListOf<Word>()
        val obj = JSONObject(wordsJson)

        val jsonArray = obj.getJSONArray("words")
        for (i in 0 until jsonArray.length()) {
            val word = Gson().fromJson(
                jsonArray.getJSONObject(i).toString(),
                Word::class.java
            )
            listWords.add(word)
        }
        return listWords
    }

    fun createLyricsPDF(context: Context, audio: AudioProc, modelWordsList: MutableList<Word>): File {

        var wordsJsontoString = ""
        var tiempow = 0.0
        var contW = 0
        for (i in 0 until modelWordsList.size) {
            tiempow += modelWordsList.get(i).time_final - modelWordsList.get(i).time_init
            if (tiempow <= 5.00 && contW <= 3) {
                contW++
                wordsJsontoString += modelWordsList.get(i).word_result + " "
            } else {
                wordsJsontoString += modelWordsList.get(i).word_result + "\n"
                tiempow = 0.0
                contW = 0
            }
        }

        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var npage = 1
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 25f
        canvas.drawText("${audio.title}", 35f, 80f, titulo)

        text.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        text.textSize = 12f

        var arrayText = wordsJsontoString.split("\n")
        var contT = 0
        var y = 120f
        var x = 45f

        for (item in arrayText) {
            if (contT < 3) {
                contT++
                canvas.drawText(item, x, y, text)
                y += 30
            } else {
                canvas.drawText(item, x, y, text)
                y += 40
                if (y >= 900f) {
                    npage++
                    pdfDocument.finishPage(pagina)
                    pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
                    pagina = pdfDocument.startPage(pageInfo)
                    canvas = pagina.canvas
                    y = 120f
                }
                contT = 0
            }
        }
        pdfDocument.finishPage(pagina)
        val file = File(Environment.getExternalStorageDirectory(), "Lyrics_${audio.title}.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return file

        }
    }

    fun createChordsPDF(
        context: Context,
        audio: AudioProc,
        modelChordsList: MutableList<Chord>,
        nom: Boolean = true
    ) : File{

        var stringChords = ""
        var chordList = mutableListOf<String>()

        var act = modelChordsList.get(0).chord_result

        for (i in 0 until modelChordsList.size) {
            if (i > 0 && modelChordsList.get(i).chord_result == act) {
                modelChordsList.set(
                    i,
                    Chord(" ", modelChordsList.get(i).time_init, modelChordsList.get(i).time_final)
                )
            } else {
                act = modelChordsList.get(i).chord_result
            }
        }

        for (item in modelChordsList) {
            println("Lista actualizada sin repeticiones: ${item.chord_result}")
        }

        for (i in 0 until (modelChordsList.get(modelChordsList.lastIndex).time_final * 10).roundToInt()) {
            chordList.add(i, " ")
        }

        for (i in 0 until modelChordsList.size) {
            if (modelChordsList.get(i).chord_result.length == 2 && i < modelChordsList.size && i != 0) {
                chordList.set(
                    (modelChordsList.get(i).time_init * 10).roundToInt() - 1,
                    modelChordsList.get(i).chord_result[0].toString()
                )
                chordList.set(
                    ((modelChordsList.get(i).time_init * 10).roundToInt()),
                    modelChordsList.get(i).chord_result[1].toString()
                )
            } else if (modelChordsList.get(i).chord_result.length == 3 && i < modelChordsList.size && i != 0) {
                chordList.set(
                    (modelChordsList.get(i).time_init * 10).roundToInt() - 2,
                    modelChordsList.get(i).chord_result[0].toString()
                )
                chordList.set(
                    ((modelChordsList.get(i).time_init * 10).roundToInt()) - 1,
                    modelChordsList.get(i).chord_result[1].toString()
                )
                chordList.set(
                    ((modelChordsList.get(i).time_init * 10).roundToInt()),
                    modelChordsList.get(i).chord_result[2].toString()
                )
            } else if (modelChordsList.get(i).chord_result.length == 4 && i < modelChordsList.size && i != 0) {
                chordList.set(
                    (modelChordsList.get(i).time_init * 10).roundToInt() - 3,
                    modelChordsList.get(i).chord_result[0].toString()
                )
                chordList.set(
                    ((modelChordsList.get(i).time_init * 10).roundToInt()) - 2,
                    modelChordsList.get(i).chord_result[1].toString()
                )
                chordList.set(
                    (modelChordsList.get(i).time_init * 10).roundToInt() - 1,
                    modelChordsList.get(i).chord_result[2].toString()
                )
                chordList.set(
                    ((modelChordsList.get(i).time_init * 10).roundToInt()),
                    modelChordsList.get(i).chord_result[3].toString()
                )
            } else {
                chordList.set(
                    (modelChordsList.get(i).time_init * 10).roundToInt(),
                    modelChordsList.get(i).chord_result
                )
            }
        }

        var stringtimeline = " 0   < " //7
        var size = chordList.size

        for (i in 0 until size) {
            stringtimeline += "-"
            if (i == size - 1) {
                stringtimeline += " > ${i / 10} \n "//7
            } else if (i % 50 == 0 && i != 0 || i == size - 1) {
                if (chordList.get(i) != " " && chordList.get(i + 1) != " " && chordList.get(i + 2) != " " && chordList.get(
                        i + 3
                    ) != " " && chordList.get(i + 4) != " "
                ) {
                    stringChords += chordList.get(i) + chordList.get(i + 1) + chordList.get(i + 2) + chordList.get(
                        i + 3
                    ) + chordList.get(i + 4)
                    chordList.set((i + 1), " ")
                    chordList.set((i + 2), " ")
                    chordList.set((i + 3), " ")
                    chordList.set((i + 4), " ")
                    stringChords += "\n"
                } else if (chordList.get(i) != " " && chordList.get(i + 1) != " " && chordList.get(i + 2) != " " && chordList.get(
                        i + 3
                    ) != " "
                ) {
                    stringChords += chordList.get(i) + chordList.get(i + 1) + chordList.get(i + 2) + chordList.get(
                        i + 3
                    )
                    chordList.set((i + 1), " ")
                    chordList.set((i + 2), " ")
                    chordList.set((i + 3), " ")
                    stringChords += "\n"
                } else if (chordList.get(i) != " " && chordList.get(i + 1) != " " && chordList.get(i + 2) != " ") {
                    stringChords += chordList.get(i) + chordList.get(i + 1) + chordList.get(i + 2)
                    chordList.set((i + 1), " ")
                    chordList.set((i + 2), " ")
                    stringChords += "\n"
                } else if (chordList.get(i) != " " && chordList.get(i + 1) != " ") {
                    stringChords += chordList.get(i) + chordList.get(i + 1)
                    chordList.set((i + 1), " ")
                    stringChords += "\n"
                } else if (chordList.get(i) != " ") {
                    stringChords += chordList.get(i) + chordList.get(i + 1)
                    chordList.set((i + 1), " ")
                    stringChords += "\n"
                } else {
                    stringChords += chordList.get(i)
                    if (i < size - 1) {
                        if (!Character.isUpperCase(chordList.get(i + 1)[0])) {
                            chordList.set((i + 1), " ")
                        }
                    }
                    stringChords += "\n"
                }
                if (i != size - 1) {
                    when ((i / 10).toString().length) {
                        1 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}   < "//5+7
                        2 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}  < "//6+7
                        3 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10} < "//7+7
                    }
                }
            } else {
                stringChords += chordList.get(i)
            }
        }

        var arrayChord = stringChords.split("\n")
        var arraytimeline = stringtimeline.split("\n")

        var array = arraytimeline
        if (arrayChord.size >= arraytimeline.size) {
            array = arrayChord
        }
        println("Compararción de largo de arreglos: ${arrayChord.size} y ${arraytimeline.size} escogí ${array.size}")

        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var chords = TextPaint()
        var timeline = TextPaint()
        var npage = 1
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 25f

        canvas.drawText("${audio.title}", 35f, 80f, titulo)

        chords.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        chords.textSize = 10f
        // chords.setColor(Color.BLUE)
        timeline.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        timeline.textSize = 10f
        // timeline.setColor(Color.RED)


        var contC = 0
        var x: Float
        var y = 130f

        for (i in 0 until array.size) {
            if (contC < 3) {
                contC++
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline.get(i), x, y, timeline)
                }
                y += 10
                x = 80f
                if (i < arrayChord.size) {
                    canvas.drawText(arrayChord.get(i), x, y, chords)
                }
                y += 30
            } else {
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline.get(i), x, y, timeline)
                }
                y += 10
                x = 80f
                if (i < arrayChord.size) {
                    canvas.drawText(arrayChord.get(i), x, y, chords)
                }
                y += 40
                if (y >= 900f) {
                    npage++
                    pdfDocument.finishPage(pagina)
                    pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
                    pagina = pdfDocument.startPage(pageInfo)
                    canvas = pagina.canvas
                    y = 130f
                }
                contC = 0
            }

        }
        pdfDocument.finishPage(pagina)

        var pre = when (nom) {
            true -> "ChordsE"
            false -> "ChordsL"
        }
        var file = when (nom) {
            true -> {
                File(Environment.getExternalStorageDirectory(), "ChordsE_${audio.title}.pdf")
            }
            false -> {
                File(Environment.getExternalStorageDirectory(), "ChordsL_${audio.title}.pdf")
            }
        }

        try {
            pdfDocument.writeTo(FileOutputStream(file))
           return file
        } catch (e: Exception) {
            e.printStackTrace()
            return file

        }


    }

    fun createLyricsChordsPDF(
        context: Context,
        audio: AudioProc,
        modelChordsList: MutableList<Chord>,
        modelWordsList: MutableList<Word>,
        nom: Boolean = true
    ): File {
        var stringWords = ""
        var stringtimeline = " "
        var wordsList = mutableListOf<String>()

        for (i in 0 until (modelWordsList.get(modelWordsList.lastIndex).time_final * 10).roundToInt()) {
            wordsList.add(i, " ")
        }
        var size = wordsList.size

        for (i in 0 until modelWordsList.size) {
            wordsList.set(
                (modelWordsList.get(i).time_init * 10).roundToInt(),
                modelWordsList.get(i).word_result
            )
        }

        var tamMax = 0
        var comp = ""
        for (i in 0 until wordsList.size) {
            if (i % 50 == 0 && i != 0 || i == size - 1) {
                comp += wordsList.get(i) + "\n"
                if (comp.length > tamMax) {
                    tamMax = comp.length
                }
                stringWords += wordsList.get(i) + "\n"
                comp = ""
            } else {
                stringWords += wordsList.get(i)
                comp += wordsList.get(i)
            }

        }

        var arrayText = stringWords.split("\n")
        var hola = Math.max(
            (modelWordsList.get(modelWordsList.lastIndex).time_final).roundToInt() / 5,
            (modelChordsList.get(modelChordsList.lastIndex).time_final).roundToInt() / 5
        )

        // println("Hlaaaa: $hola")

        for (r in 0 until hola) {
            when ((r * 5).toString().length) {
                1 -> {
                    stringtimeline += " ${r * 5}   < "
                    for (i in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(r + 1) * 5}   \n "
                }
                2 -> {
                    stringtimeline += " ${r * 5}  < "
                    for (i in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(r + 1) * 5}  \n "
                }
                3 -> {
                    stringtimeline += " ${r * 5} < "
                    for (i in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(r + 1) * 5} \n "
                }
            }

        }

        var arraytimeline = stringtimeline.split("\n")


        var chordsList = mutableListOf<String>()
        var chordsList2 = mutableListOf<String>()

        for (i in 0 until tamMax * arraytimeline.size) {
            chordsList.add(i, " ")
        }

        for (i in 0 until (modelChordsList.get(modelChordsList.lastIndex).time_final * 10).roundToInt()) {
            chordsList2.add(i, " ")
        }
        for (i in 0 until modelChordsList.size) {
            chordsList2.set(
                (modelChordsList.get(i).time_init * 10).roundToInt(),
                modelChordsList.get(i).chord_result
            )
        }


        /*     for (item in chordsList2){
                 println("C2  $item")
             }
             println("Largo de C2: ${chordsList2.size}")
             println("TamMax : $tamMax y ${Math.max(modelChordsList.size,arrayText.size)} -- ${modelChordsList.size} , ${arrayText.size}")
             println("Largo de C1: ${chordsList.size}")*/

        for (i in 0 until chordsList2.size) {
            chordsList.set(i * chordsList.size / chordsList2.size, chordsList2.get(i))
        }

        for (item in chordsList) {
            println("C1  $item")
        }
        var stringtemp = ""
        var stringChords = ""
        for (i in 0 until chordsList.size) {
            if (i % tamMax == 0 && i != 0) {
                stringtemp += "\n"
                stringChords += stringtemp
                stringtemp = ""
            } else {
                stringtemp += chordsList.get(i)

            }
        }
        var arrayChords = stringChords.split("\n")


        var h = (tamMax * 7)
        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var chords = TextPaint()
        var timeline = TextPaint()
        var npage = 0
        var pageInfo = PdfDocument.PageInfo.Builder(h + 80, 1700, npage).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 25f
        canvas.drawText("${audio.title}", 35f, 80f, titulo)

        chords.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        chords.textSize = 10f
        chords.setColor(Color.BLUE)
        timeline.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        timeline.textSize = 10f
        //
        // timeline.setColor(Color.RED)
        text.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        text.textSize = 12f

        //  var arrayChords = stringChords.split("\n")
        var tam = Math.max(Math.max(arrayChords.size, arrayText.size), arraytimeline.size)
        var contC = 0
        var x: Float
        var y = 130f

        for (i in 0 until tam) {
            if (contC < 3) {
                contC++
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline.get(i), x, y, timeline)
                }
                y += 10
                x = 85f
                if (i < arrayChords.size) {
                    canvas.drawText(arrayChords.get(i), x, y, chords)
                }
                y += 12
                if (i < arrayText.size) {
                    canvas.drawText(arrayText.get(i), x, y, text)
                }
                y += 30
            } else {
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline.get(i), x, y, timeline)
                }
                y += 10
                x = 85f
                if (i < arrayChords.size) {
                    canvas.drawText(arrayChords.get(i), x, y, chords)
                }
                y += 12
                if (i < arrayText.size) {
                    canvas.drawText(arrayText.get(i), x, y, text)
                }
                y += 40
                if (y >= 1500f) {
                    npage++
                    pdfDocument.finishPage(pagina)
                    pageInfo = PdfDocument.PageInfo.Builder(h + 80, 1700, npage).create()
                    pagina = pdfDocument.startPage(pageInfo)
                    canvas = pagina.canvas
                    y = 130f
                }
                contC = 0
            }

        }
        pdfDocument.finishPage(pagina)

        var pre = when (nom) {
            true -> "LyricChordE"
            false -> "LyricChordL"
        }
        var file = when (nom) {
            true -> {
                File(Environment.getExternalStorageDirectory(), "LyricChordE_${audio.title}.pdf")
            }
            false -> {
                File(Environment.getExternalStorageDirectory(), "LyricChordL_${audio.title}.pdf")
            }
        }

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return file

        }

    }

    fun generatePDFs(
        context: Context,
        id: Long,
        displayName: String,
        artist: String,
        data: String,
        duration: Int,
        title: String,
        english_nomenclature: String,
        latin_nomenclature: String,
        chords_lyrics_e: String,
        chords_lyrics_l: String,
        lyrics: String,
        ChordsWordsJson: String
    ): MutableList<File> {
        val modelChordsEList: MutableList<Chord> = getListModelChordEn(context, ChordsWordsJson)
        val modelWordsList: MutableList<Word> = getListModelWord(context, ChordsWordsJson)
        val modelChordsLList: MutableList<Chord> = getListModelChordLat(context, ChordsWordsJson)

        var audio = AudioProc(
            id = id,
            displayName = displayName,
            artist = artist,
            data = data,
            duration = duration,
            title = title,
            english_nomenclature = english_nomenclature,
            latin_nomenclature = latin_nomenclature,
            chords_lyrics_e = chords_lyrics_e,
            chords_lyrics_l = chords_lyrics_l,
            lyrics = lyrics
        )
        var pdfList = mutableListOf<File>()

        pdfList.add(createLyricsPDF(context, audio, modelWordsList))
        pdfList.add(createChordsPDF(context, audio, modelChordsEList))
        pdfList.add(createChordsPDF(context, audio, modelChordsLList, false))
        pdfList.add(createLyricsChordsPDF(context, audio, modelChordsEList, modelWordsList))
        pdfList.add(createLyricsChordsPDF(context, audio, modelChordsLList, modelWordsList, false))



        return pdfList

    }

    fun deleteData(audioProc: AudioProc) {
        try {
            processedAudioList.document("${audioProc.id}").delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (audioProc.lyrics.isNotEmpty()) {
            val folderL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("Lyrics_${audioProc.title}.pdf")
            val folderLAI: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("LyricChordE_${audioProc.title}.pdf")
            val folderAI: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("ChordsE_${audioProc.title}.pdf")
            val folderLAL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("LyricChordL_${audioProc.title}.pdf")
            val folderAL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("ChordsL_${audioProc.title}.pdf")

            folderL.delete()
            folderLAI.delete()
            folderAI.delete()
            folderLAL.delete()
            folderAL.delete()
        }
    }

    fun showData(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        try {
            startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            println("Algo salió mal en show")
        }
    }

    fun downloadData(url: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), null)
        try {
            startActivity(context, intent, null)
        } catch (e: ActivityNotFoundException) {
            println("Algo salió mal en download")
        }
    }

    fun startCardScreen(context: Context, toScreenPDF: Intent) {
        startActivity(context, toScreenPDF, null)
    }


}
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
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Chord
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Word
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
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

        fileName.putFile(mUri).await()
        val downloadURl = fileName.downloadUrl.await()
        file.delete()

        when (pre) {
            "Lyrics" -> {
                audio.lyrics = "$downloadURl"
                audio.chords_lyrics_e = audio.chords_lyrics_e
                audio.english_nomenclature = audio.english_nomenclature
                audio.chords_lyrics_l = audio.chords_lyrics_l
                audio.latin_nomenclature = audio.latin_nomenclature
            }
            "LyricChordE" -> {
                audio.chords_lyrics_e = "$downloadURl"
                audio.lyrics = audio.lyrics
                audio.english_nomenclature = audio.english_nomenclature
                audio.chords_lyrics_l = audio.chords_lyrics_l
                audio.latin_nomenclature = audio.latin_nomenclature
            }
            "ChordsE" -> {
                audio.english_nomenclature = "$downloadURl"
                audio.chords_lyrics_e = audio.chords_lyrics_e
                audio.lyrics = audio.lyrics
                audio.chords_lyrics_l = audio.chords_lyrics_l
                audio.latin_nomenclature = audio.latin_nomenclature
            }
            "LyricChordL" -> {
                audio.chords_lyrics_l = "$downloadURl"
                audio.english_nomenclature = audio.english_nomenclature
                audio.chords_lyrics_e = audio.chords_lyrics_e
                audio.lyrics = audio.lyrics
                audio.latin_nomenclature = audio.latin_nomenclature
            }
            "ChordsL" -> {
                audio.latin_nomenclature = "$downloadURl"
                audio.chords_lyrics_l = audio.chords_lyrics_l
                audio.english_nomenclature = audio.english_nomenclature
                audio.chords_lyrics_e = audio.chords_lyrics_e
                audio.lyrics = audio.lyrics
            }
        }

        audioFinal = audio

        return audioFinal
    }

    suspend fun uploadtoFirebase(audioP: AudioProc) {
        try {
            if (audioP.lyrics.isNotEmpty()) {
                processedAudioList.document("${audioP.id}").set(audioP).await()
        }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*fun getJsonDataFramAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }*/

    fun getListModelChordEn(context: Context, chordsJson: String): MutableList<Chord> {
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

        return deleterepeatedChords(listChords)
    }

    fun getListModelChordLat(context: Context, chordsJson: String): MutableList<Chord> {
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

       return  deleterepeatedChords(listChords)
    }

    fun getListModelWord(context: Context, wordsJson: String): MutableList<Word> {
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

    fun createLyricsPDF(
        context: Context,
        audio: AudioProc,
        modelWordsList: MutableList<Word>
    ): File {
        var wordstoString = ""
        var timeWords = 0.0
        var contW = 0
        for (i in 0 until modelWordsList.size) {
            timeWords += modelWordsList[i].time_final - modelWordsList[i].time_init
            if (timeWords <= 5.00 && contW <= 3) {
                contW++
                wordstoString += modelWordsList[i].word_result + " "
            } else {
                wordstoString += modelWordsList[i].word_result + "\n"
                timeWords = 0.0
                contW = 0
            }
        }

        val pdfDocument = PdfDocument()
        val title = TextPaint()
        val text = TextPaint()
        var npage = 1
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        title.typeface = (Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        title.textSize = 25f
        canvas.drawText(audio.title, 35f, 80f, title)

        text.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        text.textSize = 12f

        val arrayText = wordstoString.split("\n")
        var contT = 0
        var y = 120f
        val x = 45f

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
                    pdfDocument.finishPage(page)
                    pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    y = 120f
                }
                contT = 0
            }
        }
        pdfDocument.finishPage(page)
        val file = File(Environment.getExternalStorageDirectory(), "Lyrics_${audio.title}.pdf")
        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file

        }
    }

    fun createChordsPDF(
        context: Context,
        audio: AudioProc,
        modelChordsList: MutableList<Chord>,
        nom: Boolean = true
    ): File {

        val chordList = mutableListOf<Chord>()
        for (i in 0 until (modelChordsList[modelChordsList.lastIndex].time_final * 10).roundToInt()) {
             chordList.add(i, Chord(" ",0.0,0.0))
        }

        for (i in 0 until modelChordsList.size) {
            if(chordList[(modelChordsList[i].time_init * 10).roundToInt()].chord_result == " "){
            chordList[(modelChordsList[i].time_init * 10).roundToInt()] = modelChordsList[i]
            }
            else{
                println("Esta repetidote")
            }
        }

        var tamMax = 0
        var stringChords = " "
        var stringTemp = " "
        for((i, item) in chordList.withIndex()){
            if(i%50 == 0 && i!=0 ){
                stringChords+= item.chord_result +"\n"
                stringTemp+= item.chord_result +"\n"
                if(tamMax<stringTemp.length){
                    tamMax=stringTemp.length
                }
                stringTemp=""
            }else{
                stringChords+= item.chord_result
                stringTemp+= item.chord_result
            }
        }

        val arrayChord = stringChords.split("\n")

        var stringtimeline = " "

        for (i in arrayChord.indices) {
            when ((i * 5).toString().length) {
                1 -> {
                    stringtimeline += " ${i * 5}   < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5}   \n "
                }
                2 -> {
                    stringtimeline += " ${i * 5}  < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5}  \n "
                }
                3 -> {
                    stringtimeline += " ${i * 5} < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5} \n "
                }
            }
        }

        val arraytimeline = stringtimeline.split("\n")

        val pdfDocument = PdfDocument()
        val title = TextPaint()
        val chords = TextPaint()
        val timeline = TextPaint()
        var npage = 1
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        title.typeface = (Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        title.textSize = 25f

        canvas.drawText(audio.title, 35f, 80f, title)

        chords.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        chords.textSize = 10f
        timeline.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        timeline.textSize = 10f

        var contC = 0
        var x: Float
        var y = 130f

        for (i in 0 until arrayChord.size) {
            if (contC < 3) {
                contC++
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline[i], x, y, timeline)
                }
                y += 10
                x = 80f
                if (i < arrayChord.size) {
                    canvas.drawText(arrayChord[i], x, y, chords)
                }
                y += 30
            } else {
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline[i], x, y, timeline)
                }
                y += 10
                x = 80f
                if (i < arrayChord.size) {
                    canvas.drawText(arrayChord[i], x, y, chords)
                }
                y += 40
                if (y >= 900f) {
                    npage++
                    pdfDocument.finishPage(page)
                    pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    y = 130f
                }
                contC = 0
            }

        }
        pdfDocument.finishPage(page)

        val file = when (nom) {
            true -> {
                File(Environment.getExternalStorageDirectory(), "ChordsE_${audio.title}.pdf")
            }
            false -> {
                File(Environment.getExternalStorageDirectory(), "ChordsL_${audio.title}.pdf")
            }
        }

        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file
        }
}


    fun createLyricsChordsPDF(
        context: Context,
        audio: AudioProc,
        modelChordsList: MutableList<Chord>,
        modelWordsList: MutableList<Word>,
        nom: Boolean = true
    ): File {


        val wordList = mutableListOf<Word>()

        for (i in 0 .. (modelWordsList[modelWordsList.lastIndex].time_final * 10).roundToInt()) {
            wordList.add(i, Word(" ",0.0,0.0))
        }

        for (i in 0 until modelWordsList.size) {
            if(wordList[(modelWordsList[i].time_init * 10).roundToInt()].word_result == " "){
                wordList[(modelWordsList[i].time_init * 10).roundToInt()] = modelWordsList[i]
            }
            else{
                if((modelWordsList[i].time_init * 10).roundToInt()+1<wordList.size){
                wordList[(modelWordsList[i].time_init * 10).roundToInt()+1] = modelWordsList[i]}
                else{
                    wordList.add((modelWordsList[i].time_init * 10).roundToInt()+1,modelWordsList[i])
                }
            }
        }


        var tamMax = 0
        var stringWords = " "
        var stringTemp = " "
        for((i, item) in wordList.withIndex()){
            if(i%50 == 0 && i!=0 ){
                stringWords+= item.word_result +"\n"
                stringTemp+= item.word_result +"\n"
                if(tamMax<stringTemp.length){
                    tamMax=stringTemp.length
                }
                stringTemp=""
            }else if(i==wordList.size-1){
                stringWords+= item.word_result+"\n"
                stringTemp+= item.word_result+"\n"
                if(tamMax<stringTemp.length){
                    tamMax=stringTemp.length
                }
                stringTemp=""
            }
            else{
                stringWords+= item.word_result
                stringTemp+= item.word_result
            }
        }

        val arrayWord = stringWords.split("\n")

        val chordsList2 = mutableListOf<String>()
        for (i in 0 until (modelChordsList[modelChordsList.lastIndex].time_final * 10).roundToInt()) {
            chordsList2.add(i, " ")
        }
        for (i in 0 until modelChordsList.size) {
            chordsList2[(modelChordsList[i].time_init * 10).roundToInt()] = modelChordsList[i].chord_result
        }
        var lineschord=0
        for (i in 0 until chordsList2.size){
            if(i%50==0 && i!=0){
                lineschord++
            }
        }

        var stringtemp2 = ""
        var stringChords2 = ""
        for (i in 0 until tamMax * lineschord) {
            if(i<chordsList2.size){
                if (i % 50 == 0 && i != 0) {
                    stringtemp2 += "${chordsList2[i]} \n"
                    stringChords2 += stringtemp2
                    stringtemp2 = " "
                } else {
                    stringtemp2 += chordsList2[i]
                }
            }
            else{
                chordsList2.add(i," ")
                if (i % 50 == 0 && i != 0) {
                    stringtemp2 += "  \n"
                    stringChords2 += stringtemp2
                    stringtemp2 = " "
                } else {
                    stringtemp2 += " "
                }
            }

        }
        val arrayChords2 = stringChords2.split("\n")

        val lines = Math.max(lineschord,arrayWord.size)
        var stringtimeline = " "
        for (i in 0..lines) {
            when ((i * 5).toString().length) {
                1 -> {
                    stringtimeline += " ${i * 5}   < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5}   \n "
                }
                2 -> {
                    stringtimeline += " ${i * 5}  < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5}  \n "
                }
                3 -> {
                    stringtimeline += " ${i * 5} < "
                    for (s in 0 until tamMax) {
                        stringtimeline += "-"
                    }
                    stringtimeline += " > ${(i + 1) * 5} \n "
                }
            }
        }

        val arraytimeline = stringtimeline.split("\n")


        val chordsList = mutableListOf<String>()

        for (i in 0 until arrayChords2.size*tamMax) {
            chordsList.add(i, " ")
        }

        for( i in 0 until chordsList2.size){
            chordsList[i*chordsList.size/chordsList2.size] = chordsList2[i]
        }



        var stringtemp = ""
        var stringChords = ""
        for (i in 0 until chordsList.size) {
            if (i % tamMax == 0 && i != 0) {
                stringtemp += "${chordsList[i]} \n"
                stringChords += stringtemp
                stringtemp = ""
            } else {
                stringtemp += chordsList[i]
            }
        }
        val arrayChords = stringChords.split("\n")

        val h: Int = (tamMax * 7)
        val pdfDocument = PdfDocument()
        val title = TextPaint()
        val text = TextPaint()
        val chords = TextPaint()
        val timeline = TextPaint()
        var npage = 0
        var pageInfo = PdfDocument.PageInfo.Builder(1054 + 80, 1700, npage).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        title.typeface = (Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
        title.textSize = 25f
        canvas.drawText(audio.title, 35f, 80f, title)

        chords.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        chords.textSize = 10f
        chords.color = Color.BLUE
        timeline.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        timeline.textSize = 10f
        //
        // timeline.setColor(Color.RED)
        text.typeface = Typeface.createFromAsset(context.assets, "fonts/SpaceMono-Regular.ttf")
        text.textSize = 12f

        //  var arrayChords = stringChords.split("\n")
        val tam = Math.max(arrayWord.size, arraytimeline.size)
        var contC = 0
        var x: Float
        var y = 130f

        for (i in 0 until tam) {
            if (contC < 3) {
                contC++
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline[i], x, y, timeline)
                }
                y += 10
                x = 85f
                if (i < arrayChords.size) {
                   canvas.drawText(arrayChords[i], x, y, chords)
                }
                y += 12
                if (i < arrayWord.size) {
                    canvas.drawText(arrayWord[i], x, y, text)
                }
                y += 30
            } else {
                x = 30f
                if (i < arraytimeline.size) {
                    canvas.drawText(arraytimeline[i], x, y, timeline)
                }
                y += 10
                x = 85f
                if (i < arrayChords.size) {
                   canvas.drawText(arrayChords[i], x, y, chords)
                }
                y += 12
                if (i < arrayWord.size) {
                    canvas.drawText(arrayWord[i], x, y, text)
                }
                y += 40
                if (y >= 1500f) {
                    npage++
                    pdfDocument.finishPage(page)
                    pageInfo = PdfDocument.PageInfo.Builder(h + 80, 1700, npage).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    y = 130f
                }
                contC = 0
            }

        }
        pdfDocument.finishPage(page)

        val file = when (nom) {
            true -> {
                File(Environment.getExternalStorageDirectory(), "LyricChordE_${audio.title}.pdf")
            }
            false -> {
                File(Environment.getExternalStorageDirectory(), "LyricChordL_${audio.title}.pdf")
            }
        }

        return try {
            pdfDocument.writeTo(FileOutputStream(file))
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file

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

     /*  println("Tamaño modelChordsE: ${modelChordsEList.size}")
        for (i in 0 until modelChordsEList.size) {
            println(
                "getListModel -- ChordsE: ${modelChordsEList[i].chord_result} -- ${
                    modelChordsEList[i].time_init
                } -- ${modelChordsEList[i].time_final}"
            )
        }
        println("Tamaño modelChordsL: ${modelChordsLList.size}")

        for (i in 0 until modelChordsLList.size) {
            println(
                "getListModel -- ChordsL: ${modelChordsLList[i].chord_result} -- ${
                    modelChordsLList[i].time_init
                } -- ${modelChordsLList[i].time_final}"
            )
        }
        println("Tamaño modelwords: ${modelWordsList.size}")

        for (i in 0 until modelWordsList.size) {
            println(
                "modelWordsList -- Words: ${modelWordsList[i].word_result} -- ${
                    modelWordsList[i].time_init
                } -- ${modelWordsList[i].time_final}"
            )
        }*/

        val audio = AudioProc(
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
        val pdfList = mutableListOf<File>()

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
    fun deleterepeatedChords(listChords:MutableList<Chord>): MutableList<Chord> {
        var act = listChords[0].chord_result

        for (i in 0 until listChords.size) {
            if (i > 0 && listChords[i].chord_result == act) {
                listChords[i] = Chord(" ", listChords[i].time_init, listChords[i].time_final)
            } else {
                act = listChords[i].chord_result
            }
        }
        return listChords
    }

}
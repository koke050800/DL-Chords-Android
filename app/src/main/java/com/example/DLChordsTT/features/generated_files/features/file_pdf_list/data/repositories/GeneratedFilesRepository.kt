package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.text.TextPaint
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Chord
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Word
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.JsonParser
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

    fun addNewGeneratedFiles(audio: AudioProc,mUri: Uri,pre: String,file: File) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child(audio.title)
        val path = mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/') + 1))

        fileName.putFile(mUri).addOnSuccessListener { f ->
            fileName.downloadUrl.addOnSuccessListener {
                val audioP = audio
                when (pre) {
                    "L" -> {
                        audioP.lyrics = "${it}"
                        audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                        audioP.english_nomenclature = "${audio.english_nomenclature}"
                        audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                        audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                    }
                    "LAI" -> {
                        audioP.chords_lyrics_e = "${it}"
                        audioP.lyrics = "${audio.lyrics}"
                        audioP.english_nomenclature = "${audio.english_nomenclature}"
                        audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                        audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                    }
                    "AI" -> {
                        audioP.english_nomenclature = "${it}"
                        audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                        audioP.lyrics = "${audio.lyrics}"
                        audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                        audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                    }
                    "LAL" -> {
                        audioP.chords_lyrics_l = "${it}"
                        audioP.english_nomenclature = "${audio.english_nomenclature}"
                        audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                        audioP.lyrics = "${audio.lyrics}"
                        audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                    }
                    "AL" -> {
                        audioP.latin_nomenclature = "${it}"
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
                file.delete()

                try {
                    processedAudioList.document("${audioP.id}").set(audioP).addOnSuccessListener {
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    fun getJsonDataFramAsset(context:Context,fileName:String):String?{
        val jsonString:String
        try{
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }catch(e:Exception){
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getListModelChordEn(context: Context, chordsJson: String):MutableList<Chord> {
    val string = getJsonDataFramAsset(context, "Chords.json")
    val listChords = mutableListOf<Chord>()
    val obj = JSONObject(string)
    val jsonArray = obj.getJSONArray("chords")
    for (i in 0 until jsonArray.length()) {
        val chord = Gson().fromJson(
            jsonArray.getJSONObject(i).toString(),
            Chord::class.java
        )
        when(chord.chord_result){
            "a"->{chord.chord_result = "A"}
            "am"->{chord.chord_result = "Am"}
            "b"->{chord.chord_result = "B"}
            "bm"->{chord.chord_result = "Bm"}
            "c"->{chord.chord_result = "C"}
            "cm"->{chord.chord_result = "Cm"}
            "d"->{chord.chord_result = "D"}
            "dm"->{chord.chord_result = "Dm"}
            "e"->{chord.chord_result = "E"}
            "em"->{chord.chord_result = "Em"}
            "f"->{chord.chord_result = "F"}
            "fm"->{chord.chord_result = "Fm"}
            "g"->{chord.chord_result = "G"}
            "gm"->{chord.chord_result = "Gm"}

        }
        listChords.add(chord)
    }
        return listChords
}

    fun getListModelChordLat(context: Context,chordsJson: String):MutableList<Chord> {
        val string = getJsonDataFramAsset(context, "Chords.json")
        val listChords = mutableListOf<Chord>()
        val obj = JSONObject(string)

        val jsonArray = obj.getJSONArray("chords")

        for (i in 0 until jsonArray.length()) {
            val chord = Gson().fromJson(jsonArray.getJSONObject(i).toString(),
                Chord::class.java
            )
            when(chord.chord_result){
                "a"->{chord.chord_result = "La"}
                "am"->{chord.chord_result = "Lam"}
                "b"->{chord.chord_result = "Si"}
                "bm"->{chord.chord_result = "Sim"}
                "c"->{chord.chord_result = "Do"}
                "cm"->{chord.chord_result = "Dom"}
                "d"->{chord.chord_result = "Re"}
                "dm"->{chord.chord_result = "Rem"}
                "e"->{chord.chord_result = "Mi"}
                "em"->{chord.chord_result = "Mim"}
                "f"->{chord.chord_result = "Fa"}
                "fm"->{chord.chord_result = "Fam"}
                "g"->{chord.chord_result = "Sol"}
                "gm"->{chord.chord_result = "Solm"}

            }
            listChords.add(chord)

        }
        return listChords
    }

    fun getListModelWord(context: Context, wordsJson: String):MutableList<Word> {
        val string = getJsonDataFramAsset(context, "Chords.json")
        val listWords = mutableListOf<Word>()


        val obj = JSONObject( string)

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

    fun createLyricsPDF(context: Context,audioProc: AudioProc,modelWordsList: MutableList<Word>) {
        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var npage = 1
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas


        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 20f
        canvas.drawText("${audioProc.title}", 30f, 80f, titulo)

        text.typeface = Typeface.createFromAsset(context.assets,"fonts/SpaceMono-Regular.ttf")

        text.textSize = 12f

        var y = 120f

        var wordsJsontoString = ""

        var tiempow = 0.0
        var contW = 0
        for(i in 0 until modelWordsList.size){
            tiempow += modelWordsList.get(i).time_final - modelWordsList.get(i).time_init
            if(tiempow<=5.00 && contW<=3) {
                contW++
                wordsJsontoString += modelWordsList.get(i).word_result+" "
            }else{
                wordsJsontoString += modelWordsList.get(i).word_result+"\n"
                tiempow = 0.0
                contW=0
            }
        }
        println("String words ${wordsJsontoString}")

        var arrayText = wordsJsontoString.split("\n")
        var contT = 0
        var x = 30f
        y = 120f
        for (item in arrayText) {
            if(contT<3){
                contT++
                canvas.drawText(item, x, y, text)
                y += 30
            }else{
                canvas.drawText(item, x, y, text)
                y +=40
                if(y>=900f){
                    npage++
                    pdfDocument.finishPage(pagina)
                    pageInfo = PdfDocument.PageInfo.Builder(816, 1054, npage).create()
                    pagina = pdfDocument.startPage(pageInfo)
                    canvas = pagina.canvas
                    y=120f
                }
                contT=0
            }
        }
        pdfDocument.finishPage(pagina)
        val file = File(Environment.getExternalStorageDirectory(), "L_${audioProc.title}.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            addNewGeneratedFiles(audio = audioProc, file.toUri(), "L", file)
        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    fun createChordsPDF(context: Context,audioProc: AudioProc,modelChordsList: MutableList<Chord>, nom:Boolean = true ) :List<String> {
        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 20f
        canvas.drawText("${audioProc.title}", 30f, 60f, titulo)

        text.typeface = Typeface.createFromAsset(context.assets,"fonts/SpaceMono-Regular.ttf")
        text.textSize = 10f
        var y = 120f

        var stringChords=""
        var stringtimeline= " 0    < "

        var timepin = mutableListOf<String>()

        for(i in 0 until (modelChordsList.get(modelChordsList.size-1).time_final*10).roundToInt()){
                timepin.add(i," ")
        }

        var size = timepin.size

        for(i in 0 until modelChordsList.size){
           println("Asi esta redondeado ${modelChordsList.get(i).chord_result} --- ${(modelChordsList.get(i).time_init*10).roundToInt()}")

            if(modelChordsList.get(i).chord_result.length == 2 && i < modelChordsList.size && i!=0 ) {
                timepin.add((modelChordsList.get(i).time_init * 10).roundToInt()-1,modelChordsList.get(i).chord_result[0].toString())
                timepin.add(((modelChordsList.get(i).time_init * 10).roundToInt()),modelChordsList.get(i).chord_result[1].toString())
            }
            else if(modelChordsList.get(i).chord_result.length == 3 && i < modelChordsList.size && i!=0 ){
                timepin.add((modelChordsList.get(i).time_init * 10).roundToInt()-2,modelChordsList.get(i).chord_result[0].toString())
                timepin.add(((modelChordsList.get(i).time_init * 10).roundToInt())-1,modelChordsList.get(i).chord_result[1].toString())
                timepin.add(((modelChordsList.get(i).time_init * 10).roundToInt()),modelChordsList.get(i).chord_result[2].toString())

            }
            else if(modelChordsList.get(i).chord_result.length == 4 && i < modelChordsList.size && i!=0 ){
                timepin.add((modelChordsList.get(i).time_init * 10).roundToInt()-3,modelChordsList.get(i).chord_result[0].toString())
                timepin.add(((modelChordsList.get(i).time_init * 10).roundToInt())-2,modelChordsList.get(i).chord_result[1].toString())
                timepin.add((modelChordsList.get(i).time_init * 10).roundToInt()-1,modelChordsList.get(i).chord_result[2].toString())
                timepin.add(((modelChordsList.get(i).time_init * 10).roundToInt()),modelChordsList.get(i).chord_result[3].toString())
            }
            else{
                timepin.add(
                    (modelChordsList.get(i).time_init * 10).roundToInt(),
                    modelChordsList.get(i).chord_result
                )
            }
        }

        for(i in 0 until size){
            stringtimeline+="-"
            if(i == size-1){
                stringtimeline+=" > ${i/10} \n "
            }
            if(i%50==0 && i!=0 || i == size-1){
                if( timepin.get(i) != " " && timepin.get(i+1) != " "&&timepin.get(i+2) != " "&& timepin.get(i+3) != " " && timepin.get(i+4) != " ") {
                    stringChords += timepin.get(i)+timepin.get(i+1)+timepin.get(i+2)+timepin.get(i+3)+timepin.get(i+4)
                    timepin.set ((i + 1)," ")
                    timepin.set ((i + 2)," ")
                    timepin.set ((i + 3)," ")
                    timepin.set ((i + 4)," ")
                    stringChords += "\n"
                }
                else if( timepin.get(i) != " " && timepin.get(i+1) != " "&&timepin.get(i+2) != " "&& timepin.get(i+3) != " ") {
                    stringChords += timepin.get(i)+timepin.get(i+1)+timepin.get(i+2)+timepin.get(i+3)
                    timepin.set ((i + 1)," ")
                    timepin.set ((i + 2)," ")
                    timepin.set ((i + 3)," ")
                    stringChords += "\n"
                }
                else if( timepin.get(i) != " " && timepin.get(i+1) != " " && timepin.get(i+2) != " ") {
                    stringChords += timepin.get(i)+timepin.get(i+1)+timepin.get(i+2)
                    timepin.set ((i + 1)," ")
                    timepin.set ((i + 2)," ")
                    stringChords += "\n"
                }
                else if (timepin.get(i) != " " && timepin.get(i+1) != " ") {
                    stringChords += timepin.get(i)+timepin.get(i+1)
                    timepin.set ((i + 1)," ")
                    stringChords += "\n"
                }
                else if (timepin.get(i) != " " ) {
                    stringChords += timepin.get(i)+timepin.get(i+1)
                    timepin.set ((i + 1)," ")
                    stringChords += "\n"
                }
                else{
                stringChords += timepin.get(i)
                if(!Character.isUpperCase(timepin.get(i+1)[0])){
                    timepin.set ((i + 1)," ")
                 }
                stringChords += "\n"
                }
                when ((i / 10).toString().length) {
                     1 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}    < "
                     2 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}   < "
                     3 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10} < "
                     }
              }else{
                 stringChords+=timepin.get(i)
                }
        }

        var arrayChord = stringChords.split("\n")
        var contC = 0
        var x = 80f
        y= 130f
        for (item in arrayChord) {
            if(contC<3){
                contC++
                canvas.drawText(item, x, y, text)
                y += 30
            }else{
                canvas.drawText(item, x, y, text)
                y +=40
                if(y>=900f){
                  y=130f
                }
                contC=0
            }

        }

        var arraytimeline = stringtimeline.split("\n")
        var contT = 0
        x = 30f
        y = 120f

        for (item in arraytimeline) {
            if(contT<3){
                contT++
                canvas.drawText(item, x, y, text)
                y += 30
            }else{
                canvas.drawText(item, x, y, text)
                y +=40
                if(y>=900f){
                 y=120f
                }
                contT=0
            }
        }
        pdfDocument.finishPage(pagina)

        var pre = when(nom) {
            true -> "AI"
            false -> "AL"
        }
        var file = when(nom) {
            true -> {File(Environment.getExternalStorageDirectory(), "AI_${audioProc.title}.pdf")}
            false -> {File(Environment.getExternalStorageDirectory(), "AL_${audioProc.title}.pdf")}
        }

        try {
            pdfDocument.writeTo(FileOutputStream(file))
            addNewGeneratedFiles(audio = audioProc, file.toUri(), pre, file)
        }catch (e:Exception){
            e.printStackTrace()
        }

        return arrayChord
    }

    fun createLyricsChordsPDF(context: Context, audioProc: AudioProc, modelChordsList: List<String>, modelWordsList: MutableList<Word>, nom:Boolean = true){
        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var pageInfo = PdfDocument.PageInfo.Builder(1060, 2060, 1).create()
        var pagina = pdfDocument.startPage(pageInfo)
        var canvas = pagina.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 20f
        canvas.drawText("${audioProc.title}", 30f, 80f, titulo)

        text.typeface = Typeface.createFromAsset(context.assets,"fonts/SpaceMono-Regular.ttf")

        text.textSize = 12f

        var y = 120f

        var wordsJsontoString = ""
        var stringtimeline= " 0    < "

        var wordsList = mutableListOf<String>()


        for(i in 0 until (modelWordsList.get(modelWordsList.size-1).time_final*10).roundToInt()){
            wordsList.add(i,"")
        }

        var size = wordsList.size
        for(i in 0 until modelWordsList.size){
            println("Asi esta redondeado ${modelWordsList.get(i).word_result} --- ${(modelWordsList.get(i).time_init*10).roundToInt()}")
            wordsList.set((modelWordsList.get(i).time_init*10).roundToInt(),modelWordsList.get(i).word_result)
       }
        for(i in 0 until wordsList.size){
            println("aqui esta timepin: ${wordsList.get(i)} en $i")
        }
        println("Tamañote2 ${wordsList.size}")

        var cont=0
        var contW = 0
        for(i in 0 until wordsList.size){
            stringtimeline+="-"
            if(i%50==0 && i!=0 || i == size-1) {
                wordsJsontoString += wordsList.get(i)+"\n"
                when ((i / 10).toString().length) {
                    1 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}    < "
                    2 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10}   < "
                    3 -> stringtimeline += " > ${i / 10} \n ${(i + 1) / 10} < "
                }
                cont=0
                contW=0
            }else{
                cont++
                contW++
                wordsJsontoString += wordsList.get(i)+" "
            }
        }
        println("String words ${wordsJsontoString}")




        var contC = 0
        var x = 80f
        y= 130f
        for (item in modelChordsList) {
            if(contC<3){
                contC++
                canvas.drawText(item, x, y, text)
                y += 30
            }else{
                canvas.drawText(item, x, y, text)
                y +=40
                if(y>=900f){
                    y=130f
                }
                contC=0
            }

        }





        var arrayText = wordsJsontoString.split("\n")
         contC  = 0
         x = 80f
        y= 140f
        var maxlength = 0

        for (item in arrayText) {
            println("largo de la desta : ${item.length}")
               maxlength = item.length


            if(contC<3){
                contC++
                canvas.drawText(item, x, y, text)
                y += 30
            }else{
                canvas.drawText(item, x, y, text)
                y +=40
                  if(y>=900f){
                      y=140f
                  }
                contC=0
            }
        }

          var arraytimeline = stringtimeline.split("\n")
          var contT = 0
          var plus = 0
          x = 30f
          y = 120f


          for (item in arraytimeline) {
              println("${item.length} ---- ${maxlength}")
              if(item.length < maxlength && item.length > 5 ){
                  for(i in 0 until maxlength-item.length){
                            item
                  }
              }
              if(contT<3){
                  contT++
                  canvas.drawText(item, x, y, text)
                  y += 30
              }else{
                  canvas.drawText(item, x, y, text)
                  y +=40
                  if(y>=900f){
                      y=120f
                  }
                  contT=0
              }
          }


        pdfDocument.finishPage(pagina)

        var pre = when(nom) {
            true -> "LAI"

            false -> "LAL"
        }
        var file = when(nom) {
            true -> {File(Environment.getExternalStorageDirectory(), "LAI_${audioProc.title}.pdf")
            }
            false -> {File(Environment.getExternalStorageDirectory(), "LAL_${audioProc.title}.pdf")}
            //  else -> File(Environment.getExternalStorageDirectory(), "AI_${audioProc.title}.pdf")
        }


        try {
            pdfDocument.writeTo(FileOutputStream(file))
            addNewGeneratedFiles(audio = audioProc, file.toUri(), pre, file)
        }catch (e:Exception){
            e.printStackTrace()
        }





































    }

    fun generatePDFs(context: Context, audioProc: AudioProc, chordsJson : String , wordsJson: String  ){
        val modelChordsEList: MutableList<Chord> = getListModelChordEn(context, chordsJson)
        val modelWordsList: MutableList<Word> = getListModelWord(context, wordsJson)
        val modelChordsLList: MutableList<Chord> = getListModelChordLat(context, chordsJson)


        createLyricsPDF(context,audioProc,modelWordsList)
        var chordEList =  createChordsPDF(context,audioProc, modelChordsEList)
        var chordLList = createChordsPDF(context,audioProc, modelChordsLList, false)
        createLyricsChordsPDF(context,audioProc,chordEList,modelWordsList)
        createLyricsChordsPDF(context,audioProc,chordLList,modelWordsList,false)

        /*     Thread.sleep(2000)
             file = createChordsPDF(audioProc, modelChordsEList, modelWordsList,)
             addNewGeneratedFiles(audio = audioProc, file.toUri(), "AI", file)
             Thread.sleep(2000)
             file = createChordsPDF(audioProc, modelChordsLList, modelWordsList,false)
              addNewGeneratedFiles(audio = audioProc, file.toUri(), "AL", file)
             Thread.sleep(2000)*/

            // file = createChordsPDF(audioProc, modelChordsEList, modelWordsList,)
           // addNewGeneratedFiles(audio = audioProc, file.toUri(), "LAI", file)
           // file = createChordsPDF(audioProc, modelChordsLList, modelWordsList,)
           // addNewGeneratedFiles(audio = audioProc, file.toUri(), "LAL", file)

    }



/*
    fun createPDF(context: Context, audioProc: AudioProc,typePDF: String) {




        //-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------

        var tiempo = 0.0
        for(i in 0 until modelChords.size){
             tiempo += modelChords.get(i).time_final - modelChords.get(i).time_init
            if(modelChords.get(i).time_init>=modelWords.get(0).time_init && tiempo<=5.0) {
                stringChords += modelChords.get(i).chord_result+" "
            }else{
                stringChords += "\n"
                tiempo = 0.0
            }
            }
        println("String chords ${stringChords}")

        var arrayChord = stringChords.split("\n")
        var contC : Int = 0
        y=-253f
        for (item in arrayChord) {
            if(contC<4){
                contC++
                canvas.drawText(item, 30f, y, text)
                y += 25
            }else{
                y +=20
                println("Brincoooo")
                contC=0
            }
        }
//--------------------------------------------------------------------------------------------------
        pdfDocument.finishPage(pagina1)


        val file = File(Environment.getExternalStorageDirectory(), "${pre}_${audioProc.title}.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            addNewGeneratedFiles(audio = audioProc, file.toUri(), "L", file)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
*/
    fun deleteData(audioProc: AudioProc) {
        try {
            processedAudioList.document("${audioProc.id}").delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (audioProc.lyrics.isNotEmpty()) {
            val folderL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("L_${audioProc.title}.pdf")
            val folderLAI: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("LAI_${audioProc.title}.pdf")
            val folderAI: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("AI_${audioProc.title}.pdf")
            val folderLAL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("LAL_${audioProc.title}.pdf")
            val folderAL: StorageReference =
                FirebaseStorage.getInstance().reference.child(audioProc.title)
                    .child("AL_${audioProc.title}.pdf")
            folderL.delete()
            folderLAI.delete()
            folderAI.delete()
            folderLAL.delete()
            folderAL.delete()
        }
    }

    fun showData(url: String, context: Context) {

        val intent = Intent(Intent.ACTION_VIEW)
        println("Este es el url${url}")
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        //val newIntent = Intent.createChooser(intent)
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
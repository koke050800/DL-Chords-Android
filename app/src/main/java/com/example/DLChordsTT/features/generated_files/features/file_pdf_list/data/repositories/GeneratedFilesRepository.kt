package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories

import DLChordsTT.R
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.text.TextPaint
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.convertTo
import androidx.core.net.toUri
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.Result
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Files
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.view_models.GeneratedFilesViewModel
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeneratedFilesRepository
@Inject
constructor(
    private val processedAudioList: CollectionReference

) {
    fun addNewGeneratedFiles(audio: AudioProc, mUri: Uri, pre: String, file :File) {

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
                    else ->  {
                        audioP.latin_nomenclature = "${audio.latin_nomenclature}"
                        audioP.chords_lyrics_l = "${audio.chords_lyrics_l}"
                        audioP.english_nomenclature = "${audio.english_nomenclature}"
                        audioP.chords_lyrics_e = "${audio.chords_lyrics_e}"
                        audioP.lyrics = "${audio.lyrics}"
                    }
                }
                file.delete()
                try {
                    processedAudioList.document("${audioP.id}").set(audioP)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun createPDF(audioProc: AudioProc, textD: String, typePDF: String){
        var pdfDocument = PdfDocument()
        var titulo = TextPaint()
        var text = TextPaint()
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(pageInfo)
        var canvas = pagina1.canvas

        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 20f
        canvas.drawText(audioProc.displayName, 10f, 150f, titulo)

        text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        text.textSize = 14f
        var y = 200f
        var arrayText = textD.split("\n")
        for (item in arrayText) {
            canvas.drawText(item, 10f, y, text)
            y += 15
        }
        pdfDocument.finishPage(pagina1)

        var pre = when (typePDF) {
            "L" -> "L"
            "LAI" -> "LAI"
            "AI" -> "AI"
            "LAL" -> "LAL"
            "AL" -> "AL"
            else -> ""
        }
        val file = File(Environment.getExternalStorageDirectory(), "${pre}_${audioProc.title}.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            addNewGeneratedFiles(audio = audioProc, file.toUri(), pre,file)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun deleteData(audioProc: AudioProc){
        try {
            processedAudioList.document("${audioProc.id}").delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
 if(audioProc.lyrics.isNotEmpty()) {
     val folderL: StorageReference = FirebaseStorage.getInstance().reference.child(audioProc.title)
         .child("L_${audioProc.title}.pdf")
     val folderLAI: StorageReference =
         FirebaseStorage.getInstance().reference.child(audioProc.title)
             .child("LAI_${audioProc.title}.pdf")
     val folderAI: StorageReference = FirebaseStorage.getInstance().reference.child(audioProc.title)
         .child("AI_${audioProc.title}.pdf")
     val folderLAL: StorageReference =
         FirebaseStorage.getInstance().reference.child(audioProc.title)
             .child("LAL_${audioProc.title}.pdf")
     val folderAL: StorageReference = FirebaseStorage.getInstance().reference.child(audioProc.title)
         .child("AL_${audioProc.title}.pdf")
     folderL.delete()
     folderLAI.delete()
     folderAI.delete()
     folderLAL.delete()
     folderAL.delete()
 }
    }

    fun showData(url:String, context: Context){

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val newIntent = Intent.createChooser(intent, "Open File")
        try {
            ContextCompat.startActivity(context, newIntent, null)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }
    }

    fun downloadData(url:String, context: Context){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url),null)
        try {
            startActivity(context,intent,null)
        } catch (e: ActivityNotFoundException) {
            // Instruct the user to install a PDF reader here, or something
        }

    }
    fun startCardScreen(context: Context, toScreenPDF: Intent){

        startActivity(context, toScreenPDF, null)

    }
}
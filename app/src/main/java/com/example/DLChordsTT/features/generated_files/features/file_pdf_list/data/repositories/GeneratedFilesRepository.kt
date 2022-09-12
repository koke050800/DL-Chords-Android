package com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.repositories

import DLChordsTT.R
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.text.TextPaint
import android.widget.Toast
import androidx.compose.ui.graphics.Paint
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.AudioProc
import com.example.DLChordsTT.features.audio_list.features.processed_audio_list.data.models.Result
import com.example.DLChordsTT.features.generated_files.features.file_pdf_list.data.models.Files
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
  //  private val processedAudioList: CollectionReference

) {


fun addNewGeneratedFiles(file :File,mUri: Uri){
    val data = Firebase.database
    val myRef = data.getReference("prueba")
    println("Entre al add Files")

    val folder: StorageReference = FirebaseStorage.getInstance().reference.child("prueba")
    val path = mUri.lastPathSegment.toString()
    val fileName :StorageReference =folder.child(path.substring(path.lastIndexOf('/'+1)))

    fileName.putFile(mUri).addOnSuccessListener {
        fileName.downloadUrl.addOnSuccessListener { uri ->
            val hashMap = HashMap<String,String>()
            hashMap["link"]=java.lang.String.valueOf(uri)

            myRef.child(myRef.push().key.toString()).setValue(hashMap)

        }

    }
}

    fun createPDF(title: String, textD: String) {


        println("entre a este rollo")

        var pdfDocument = PdfDocument()
        var paint = Paint()
        var titulo = TextPaint()
        var text = TextPaint()
        var pageInfo = PdfDocument.PageInfo.Builder(816, 1054, 1).create()
        var pagina1 = pdfDocument.startPage(pageInfo)
        var canvas = pagina1.canvas


        titulo.setTypeface((Typeface.create(Typeface.DEFAULT, Typeface.BOLD)))
        titulo.textSize = 20f
        canvas.drawText(title, 10f, 150f, titulo)
        println("Ya hice el titulo")

        text.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        text.textSize = 14f
        var y = 200f
        var arrayText = textD.split("\n")
        for (item in arrayText) {
            canvas.drawText(item, 10f, y, text)
            y += 15
        }
        pdfDocument.finishPage(pagina1)
        println("Ya pegue el texto")

        val file = File(Environment.getExternalStorageDirectory(), "holis.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            println("Ya lo creeeeeeeeeee")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }






}
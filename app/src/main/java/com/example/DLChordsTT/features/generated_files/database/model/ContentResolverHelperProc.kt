package com.example.DLChordsTT.features.generated_files.database.model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ContentResolverHelperProc  @Inject
constructor(@ApplicationContext val context: Context) {

    val database = Firebase.database
    val myRef = database.getReference("audios")
    var dataBaseAudios: List<AudioProc> = listOf<AudioProc>(AudioProc(
        displayName = "Me iré con ella2",
        id = 0L,
        artist = "Santa Fe Klan",
        data = "",
        duration = 267491,
        title = "TITLE"
    ))

init{
    getBDAudioData()
   // addfiles()
}

fun addfiles(){

    val database = Firebase.database
    val myRef = database.getReference("files")
    val refe = myRef.child("files");

    var users: HashMap<String, FileG> = HashMap<String, FileG> ()

    users.put(""+0, FileG(0,"Hola", File("/Prueba.pdf"),File("/Prueba.pdf"),File("/Prueba.pdf"),File("/Prueba.pdf"),File("/Prueba.pdf")));
    users.put(""+1,  FileG(1,"Hola1", File("/Prueba1.pdf"),File("/Prueba1.pdf"),File("/Prueba1.pdf"),File("/Prueba1.pdf"),File("/Prueba1.pdf")));

    myRef.setValue(users)

}

    fun getBDAudioData(){
      myRef.addValueEventListener(object : ValueEventListener {
          override fun onDataChange(snapshot: DataSnapshot) {
              val value = snapshot.getValue().toString()

              Log.d(ContentValues.TAG, "Value is: " + value)
              val dataBaseAudios: List<AudioProc> = snapshot.children.map { snapshot ->
                  snapshot.getValue(AudioProc::class.java)!!
              }

              if(dataBaseAudios.isNotEmpty()){
                  println(" Hola On data changed " + dataBaseAudios.size)
                  println(" Hola On data changed " + dataBaseAudios.toString())
                  añadir(dataBaseAudios)
              }else{

                  println("Eh mijou esta vaciaaaaaaa")
              }


          }

            // println("Valor en Ondata Changed "+dataBaseAudios.get(0).artist)
      override fun onCancelled(error: DatabaseError) {
              Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
          }

      })
     println("Estoy extrayndo los datos")
   //  println("Holaaaa2322a" + this.dataBaseAudios.size)

  }
    fun añadir( dataBaseAudios: List<AudioProc> ) {

        println(" Hola en añadir " + dataBaseAudios.size)

        this.dataBaseAudios = dataBaseAudios
        println(" Hola en añadir " + this.dataBaseAudios.size)
        println(" Hola y datos " + this.dataBaseAudios.toString())

    }

    fun getBDAudioData2(): List<AudioProc>{
        return dataBaseAudios
    }





}


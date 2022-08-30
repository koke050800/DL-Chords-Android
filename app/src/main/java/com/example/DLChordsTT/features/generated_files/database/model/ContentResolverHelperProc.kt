package com.example.DLChordsTT.features.generated_files.database.model

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ContentResolverHelperProc @Inject
constructor(@ApplicationContext val context: Context) {

    fun addfiles() {

        val database = Firebase.database
        val myRef = database.getReference("files")
        val refe = myRef.child("files");

        var users: HashMap<String, FileG> = HashMap<String, FileG>()

        users.put(
            "" + 0,
            FileG(
                0,
                "Hola",
                File("/Prueba.pdf"),
                File("/Prueba.pdf"),
                File("/Prueba.pdf"),
                File("/Prueba.pdf"),
                File("/Prueba.pdf")
            )
        );
        users.put(
            "" + 1,
            FileG(
                1,
                "Hola1",
                File("/Prueba1.pdf"),
                File("/Prueba1.pdf"),
                File("/Prueba1.pdf"),
                File("/Prueba1.pdf"),
                File("/Prueba1.pdf")
            )
        );

        myRef.setValue(users)

    }

    @WorkerThread
    fun getDataBD(): List<AudioProc> {
        return getBDAudioData()

    }





    private fun getBDAudioData(): MutableList<AudioProc> {

        val database = Firebase.database
        val myRef = database.getReference("audios")
        println("database= ${database}")
        val audioListProcessed = mutableListOf<AudioProc>()


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue()
                Log.d(ContentValues.TAG, "Value is: " + value)
                    snapshot.children.forEach { println("Hola foreach_ ${it.getValue<AudioProc>()}" )
                        var audio : AudioProc? = it.getValue(AudioProc::class.java)
                        println("var audio: ${audio?.artist}")
                        if (audio != null) {
                            audioListProcessed += audio
                            println("Size de lista ${audioListProcessed.size}" )
                        }
                        println(" pa calar2 " + audioListProcessed.size)

                    }
                println("Estoy vacia ??? ${audioListProcessed.isEmpty()}")
                println(" pa calar " + audioListProcessed.size)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
                //println("Holaaa")

        }

        )


        println(" Antes del return " + audioListProcessed.size)

        return audioListProcessed
    }


}


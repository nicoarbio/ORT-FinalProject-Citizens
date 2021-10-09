package com.dTeam.ciudadanos.repositories
import android.util.Log
import com.dTeam.ciudadanos.entities.Reclamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReclamoRepository (){
/*
    private var reclamoList : MutableList<Reclamo> = mutableListOf()
    val db = Firebase.firestore
    init {
        db.collection("reclamos")
            .whereEqualTo("usuario", "fperchuk@hotmail.com") //TODO: Acá poner el mail del usuario logueado!
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (reclamo in snapshot) {
                        reclamoList.add(reclamo.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Test", "Error al obtener documentos: ", exception)
            }
    }

    fun getReclamos() : MutableList<Reclamo>{
        return reclamoList
    }

    fun getDescription(pos:Int) : String{
        return reclamoList[pos].descripcion
    }

   // fun getImage(pos:Int) : String{
       // return reclamoList[pos].urlImage
  //  }*/
}
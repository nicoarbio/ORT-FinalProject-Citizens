package com.dTeam.ciudadanos.viewmodels

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.*
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.entities.Observacion
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.fragments.FragmentNuevoReclamo
import com.dTeam.ciudadanos.fragments.FragmentNuevoReclamoDirections
import com.google.android.material.snackbar.Snackbar
import com.dTeam.ciudadanos.entities.Subcategoria
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import kotlin.math.log

class ReclamoViewModel : ViewModel() {

    val db = Firebase.firestore
    private var reclamoList : MutableList<Reclamo> = mutableListOf()
    val listadoReclamos = MutableLiveData<MutableList<Reclamo>>()
    var listadoImgs : MutableList<String> = mutableListOf()
    var reclamo = MutableLiveData<Reclamo>()

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    init {
        reclamo.value= Reclamo()
    }

    fun generarReclamo(reclamoNuevo : Reclamo, imagenes:List<Uri>):Boolean {
        var reclamoGenerado = true
        try {
            viewModelScope.launch {
                var uploadTask: UploadTask
                for (img in imagenes) {

                    val imgReclamo = storageRef.child("reclamos/${img.lastPathSegment}")
                    uploadTask = imgReclamo.putFile(img)
                    uploadTask.await()
                    if (uploadTask.isSuccessful()) {
                        var url = storageRef.child("reclamos/${img.lastPathSegment}").downloadUrl.await()
                        reclamoNuevo.imagenes.add(url.toString())
                    }

                    Log.d("test", "Dentro task succesful: " + reclamoNuevo.toString())
                }
                Log.d("test", "Fuera de scope: " + reclamoNuevo.toString())
                reclamo.value=reclamoNuevo
                db.collection("reclamos")
                    .add(reclamoNuevo)
            }

        } catch (e: Exception) {
                Log.d("Test", "Error al generar reclamo: " + e)
                reclamoGenerado = false
            }
        return reclamoGenerado


        /*reclamo.postValue(reclamoNuevo)
        db.collection("reclamos")
            .add(reclamoNuevo)
            .addOnFailureListener() {
                Log.d("Test", "Error al generar reclamo")
                Snackbar.make(v,"Ocurrió un error. Vuelva a intentar mas tarde", Snackbar.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                Log.d("Test", "Reclamo OKK")
                var uploadTask : UploadTask
                for (img in imagenes){
                    val imgReclamo = storageRef.child("reclamos/${img.lastPathSegment}")
                    uploadTask = imgReclamo.putFile(img)
                    uploadTask.addOnFailureListener {
                        Snackbar.make(v,"Ocurrió un error. Vuelva a intentar mas tarde", Snackbar.LENGTH_SHORT).show()
                        //TODO: si falla la carga de alguna img volver para atrás la carga del resto y salir del for para que no siga cargando
                    }
                        .addOnSuccessListener { taskSnapshot ->
                            //listadoImgs.add(taskSnapshot.metadata!!.path)
                            db.collection("reclamos").document(it.id).update("imagenes", FieldValue.arrayUnion(taskSnapshot.metadata!!.path))
                            //Log.d("Test", taskSnapshot.metadata!!.path)
                            v.findNavController().navigate(action)
                        }

            }
        }*/
    }

    fun getReclamos() {
         viewModelScope.launch {
             reclamoList.clear()
             try {
                 val reclamos = db.collection("reclamos")
                     .whereEqualTo("usuario", "UID_DEL_USUARIO") //TODO: Acá poner el UID del usuario logueado!
                     .get()
                     .await()
                 if (reclamos != null) {
                     for (reclamo in reclamos) {
                         reclamoList.add(reclamo.toObject())
                     }
                     listadoReclamos.value =  reclamoList
                 }
             }catch (e: Exception){
                 Log.w("Test", "Error al obtener documentos: ", e)
             }
         }
    }
    fun agregarObser(obserNuevo: Observacion): Boolean{
        try {
            // obtener el id del reclamo actual en la base de dato
            val ref = db.collection("reclamos").document(reclamo.value!!.documentId!!)
            ref.update("observaciones", FieldValue.arrayUnion(obserNuevo))
            reclamo.value!!.observaciones.add(obserNuevo)
            return true
        } catch (e : Exception){
            Log.w("Test", "Error al  agregar observacion: ", e)
            return false
        }
    }

    fun obtenerUrlImg(path:String):String{
        storageRef.child(path).downloadUrl.addOnSuccessListener {
            //Obtenemos URL de la img
        }.addOnFailureListener {
            //Error
        }
        return ""
    }

    fun getCategoria(): String? {
        return reclamo.value?.categoria
    }
    fun getSubcategoria(): String? {
        return reclamo.value?.subCategoria
    }
    fun getDireccion(): String? {
        return reclamo.value?.direccion
    }
    fun getDescripcion(): String? {
        return reclamo.value?.descripcion
    }
    fun getEstado(): String? {
        return reclamo.value?.estado
    }
    fun getObservaciones(): MutableList<Observacion>? {
        return reclamo.value?.observaciones
    }
    fun setCategoria(categoria : String){
        reclamo.value!!.categoria=categoria
    }
    fun setSubcategoria(subcategoria: String){
        reclamo.value!!.subCategoria=subcategoria
    }
}
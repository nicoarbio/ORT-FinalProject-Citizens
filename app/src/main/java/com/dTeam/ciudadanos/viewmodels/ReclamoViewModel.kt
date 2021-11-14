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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

class ReclamoViewModel : ViewModel() {

    val db = Firebase.firestore
    private var reclamoList : MutableList<Reclamo> = mutableListOf()
    val listadoReclamos = MutableLiveData<MutableList<Reclamo>>()
    var reclamo = MutableLiveData<Reclamo>()
    var reclamoGeneradoOk = MutableLiveData<Boolean>()
    var imgEstadoReclamo = MutableLiveData<Uri>()

    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    var estadoGuardadoOk = MutableLiveData<Boolean>()

    init {
        reclamo.value= Reclamo()
    }

    fun generarReclamo(reclamoNuevo : Reclamo, imagenes:List<Uri>) {
        viewModelScope.launch {
            try {
                    var uploadTask: UploadTask
                    for (img in imagenes) {
                        val imgReclamo = storageRef.child("reclamos/${img.lastPathSegment}")
                        uploadTask = imgReclamo.putFile(img)
                        uploadTask.await()
                        if (uploadTask.isSuccessful()) {
                            var url = storageRef.child("reclamos/${img.lastPathSegment}").downloadUrl.await()
                            reclamoNuevo.imagenes.add(url.toString())
                        }
                    }
                    reclamoNuevo.observaciones.add(Observacion("Ciudadano", "Reclamo Inicializado", getFecha()))
                    reclamo.value=reclamoNuevo
                    db.collection("reclamos")
                        .add(reclamoNuevo)
                        .await()
                    reclamoGeneradoOk.value = true

            } catch (e: Exception) {
                Log.d("Test", "Error al generar reclamo: " + e)
                reclamoGeneradoOk.value = false
            }
        }
    }

    fun getReclamos(usuario:String) {
         viewModelScope.launch {
             reclamoList.clear()
             try {
                 val reclamos = db.collection("reclamos")
                     .whereEqualTo("usuario", usuario)
                     .get()
                     .await()
                 if (reclamos != null) {
                     for (reclamo in reclamos) {
                         reclamoList.add(reclamo.toObject())
                     }
                     listadoReclamos.value =  reclamoList
                 }
             }catch (e: Exception){
                 Log.w("Test", "Error al obtener reclamos: ", e)
             }
         }
    }
    fun agregarObser(obserNuevo: Observacion) {
        viewModelScope.launch {
            try {
                // obtener el id del reclamo actual en la base de dato
                val ref = db.collection("reclamos").document(reclamo.value!!.documentId!!)
                ref.update("observaciones", FieldValue.arrayUnion(obserNuevo)).await()
                reclamo.value!!.observaciones.add(obserNuevo)
                estadoGuardadoOk.value = true
            } catch (e : Exception){
                estadoGuardadoOk.value = false
                Log.w("Test", "Error al  agregar observacion: ", e)
            }
        }

    }

    fun getImgEstado(){
        viewModelScope.launch {
            val gsReference = storage.getReferenceFromUrl("gs://ort-proyectofinal.appspot.com/")
            val img = gsReference.child("estados").child(reclamo.value!!.estado + ".png").downloadUrl.await()
            imgEstadoReclamo.value = img
        }

    }

    fun getFecha(): String {
        val currentDateTime = LocalDateTime.now()
        return currentDateTime.format(DateTimeFormatter.ISO_DATE)
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
package com.dTeam.ciudadanos.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Categoria
import com.dTeam.ciudadanos.entities.Subcategoria
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.net.URI

class CategoriaViewModel: ViewModel() {
    val db = Firebase.firestore
    val storage = FirebaseStorage.getInstance()
    private var categoriaList : MutableList<Categoria> = mutableListOf()
    private var subcategoriaList : MutableList<Subcategoria> = mutableListOf()
    val listadoCategorias = MutableLiveData<MutableList<Categoria>>()
    val listadoSubcategoria = MutableLiveData<MutableList<Subcategoria>>()
    var imgCategoria = MutableLiveData<Uri>()
    private val _idCategoria = MutableLiveData<String>()

    fun getCategorias() {
        viewModelScope.launch {
            categoriaList.clear()
            try {
                val categorias = db.collection("categoriasReclamos")
                    .get()
                    .await()
                if (categorias != null) {
                    for (categoria in categorias) {
                        categoriaList.add(categoria.toObject())
                    }
                    listadoCategorias.value =  categoriaList
                }
            }catch (e: Exception){
                Log.w("Test", "Error al obtener documentos: ", e)
            }
        }
    }
    fun getSubcategorias() {
        viewModelScope.launch {
            subcategoriaList.clear()
            try {
                val subCategorias = db.collection("categoriasReclamos")
                    .document(_idCategoria.value.toString())
                    .collection("subCategorias")
                    .get()
                    .await()
                if (subCategorias != null) {
                    for (subCategoria in subCategorias) {
                        subcategoriaList.add(subCategoria.toObject())
                    }
                    listadoSubcategoria.value =  subcategoriaList
                }
            }catch (e: Exception){
                Log.w("Test", "Error al obtener documentos: ", e)
            }
        }
    }
    fun getImgCategoria(categoria: String){
        viewModelScope.launch {
            val gsReference = storage.getReferenceFromUrl("gs://ort-proyectofinal.appspot.com/")
            val img = gsReference.child("categorias").child(categoria + ".png").downloadUrl.await()
            imgCategoria.value = img
        }

    }
    fun setDocumentId(idCategoria: String){
           _idCategoria.value=idCategoria
    }
}
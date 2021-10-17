package com.dTeam.ciudadanos.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Categoria
import com.dTeam.ciudadanos.entities.Reclamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CategoriaViewModel: ViewModel() {
    val db = Firebase.firestore
    private var categoriaList : MutableList<Categoria> = mutableListOf()
    val listadoCategorias = MutableLiveData<MutableList<Categoria>>()

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
}
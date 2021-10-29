package com.dTeam.ciudadanos.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.entities.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.*

class UsuarioViewModel : ViewModel() {



    val db = Firebase.firestore
    //private var reclamoList : MutableList<Reclamo> = mutableListOf()
    //val listadoReclamos = MutableLiveData<MutableList<Reclamo>>()




    fun agregarUsuario(usuarioNuevo : Usuario):Boolean{
        var guardadoOk = false
        viewModelScope.launch {
            try {
                db.collection("usuarios")
                    .add(usuarioNuevo)
                    .await()
                guardadoOk = true
            }catch(e : Exception) {
                Log.w("Test", "Error al generar Usuario : ", e)
            }

        }
        return guardadoOk
    }





}
package com.dTeam.ciudadanos.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import java.util.Date

class Usuario(rol :  String, nombre: String, apellido: String, dni :  String, fechaDeNacimiento: Date,email: String,contrasenia: String,telefono: String,
              direccion: String,codigoPostal: String) {
    @DocumentId
    private val documentId: String? = null
    var rol: String
    var nombre: String
    var apellido: String
    var dni: String
    var fechaDeNacimiento: Date
    var email : String
    var contrasenia: String
    var telefono: String
    var direccion: String
    var codigoPostal : String
    var reclamos : MutableList<Reclamo>



init {
    this.rol = rol
    this.nombre = nombre
    this.apellido = apellido
    this.dni = dni
    this.fechaDeNacimiento = fechaDeNacimiento
    this.email = email
    this.contrasenia = contrasenia
    this.telefono = telefono
    this.direccion = direccion
    this.codigoPostal = codigoPostal
    this.reclamos = mutableListOf()
}


}
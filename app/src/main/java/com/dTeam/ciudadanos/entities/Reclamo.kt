package com.dTeam.ciudadanos.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

class Reclamo(categoria: String, subCategoria: String, direccion: String, descripcion: String, usuario:String, estado: String, responsable: String) {

    @DocumentId
    var documentId: String? = null
    var categoria: String
    var subCategoria: String
    var direccion: String
    var descripcion: String
    var usuario: String
    var estado: String
    var responsable: String
    var observaciones: MutableList<Observacion>
    var imagenes: MutableList<String>

    constructor() : this("","","","","","","")

    init {
        this.categoria = categoria
        this.subCategoria = subCategoria
        this.direccion = direccion
        this.descripcion = descripcion
        this.usuario = usuario
        this.estado= estado
        this.responsable= responsable
        this.observaciones = mutableListOf()
        this.imagenes = mutableListOf()
    }
}
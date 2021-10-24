package com.dTeam.ciudadanos.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.DocumentId

class Reclamo(categoria: String, subCategoria: String, direccion: String, descripcion: String, usuario:String, estado: String, responsable: String) :
    Parcelable {
    @DocumentId
    private val documentId: String? = null
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

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(categoria)
        writeString(subCategoria)
        writeString(direccion)
        writeString(descripcion)
        writeString(usuario)
        writeString(estado)
        writeString(responsable)
    }

    override fun toString(): String {
        return "Reclamo(categoria='$categoria', subCategoria='$subCategoria', direccion='$direccion', descripcion='$descripcion', usuario='$usuario', estado='$estado', responsable='$responsable', observaciones=$observaciones)"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Reclamo> = object : Parcelable.Creator<Reclamo> {
            override fun createFromParcel(source: Parcel): Reclamo = Reclamo(source)
            override fun newArray(size: Int): Array<Reclamo?> = arrayOfNulls(size)
        }
    }
}
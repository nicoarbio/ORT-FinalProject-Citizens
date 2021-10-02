package com.dTeam.ciudadanos.entities

import android.os.Parcel
import android.os.Parcelable
import android.text.Editable

class Reclamo(categoria: String, subCategoria: String, direccion: String, descripcion: String, usuario:String) :
    Parcelable {
    var categoria: String
    var subCategoria: String
    var direccion: String
    var descripcion: String
    var usuario: String

    constructor() : this("","","","","")

    init {
        this.categoria = categoria
        this.subCategoria = subCategoria
        this.direccion = direccion
        this.descripcion = descripcion
        this.usuario = usuario
    }

    constructor(source: Parcel) : this(
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
    }

    /*override fun toString(): String {
        return "Mascota(nombre='$nombre', tipo='$tipo', raza='$raza', edad=$edad, urlImage='$urlImage')"
    }*/

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Reclamo> = object : Parcelable.Creator<Reclamo> {
            override fun createFromParcel(source: Parcel): Reclamo = Reclamo(source)
            override fun newArray(size: Int): Array<Reclamo?> = arrayOfNulls(size)
        }
    }
}
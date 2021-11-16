package com.dTeam.ciudadanos.entities

import com.dTeam.ciudadanos.network.OrionApi
import com.squareup.moshi.Json

class Usuario(
    @Json(name = "id")
    var documentId : String,
    var type:String,
    var dni:  String,
    var apellido: String,
    var codigoPostal: String,
    var direccion: String,
    var fechaDeNacimiento: String,
    var nombre: String,
    var rol: String, //Ciudadano/Ministerio/Responsable
    var telefono: String,
    var email: String,
    var isEnabled : String
    )
{
    constructor() : this("","","","","","", "", "", "","","",OrionApi.USER_ENABLED)
    constructor(typ:String, usrRol:String, nom:String, ap:String, fnac:String, dni:String, tlfno:String, mail:String, direc:String, enabled:String)  : this("",typ,dni,ap,"",direc, fnac, nom, usrRol,tlfno,mail,enabled)

    override fun toString(): String {
        return "Usuario(documentId='$documentId', type='$type', dni='$dni', apellido='$apellido', codigoPostal='$codigoPostal', direccion='$direccion', fechaDeNacimiento='$fechaDeNacimiento', nombre='$nombre', rol='$rol', telefono='$telefono', email='$email', isEnabled='$isEnabled')"
    }
}
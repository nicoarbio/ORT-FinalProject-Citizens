package com.dTeam.ciudadanos.entities

import java.util.Date

class Usuario(
    var id: String,
    var rol: String,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var fechaDeNacimiento: Date,
    var email : String,
    var contrasenia: String,
    var telefono: String,
    var direccion: String,
    var codigoPostal : String,
    var reclamos : MutableList<Reclamo>
)
{

}
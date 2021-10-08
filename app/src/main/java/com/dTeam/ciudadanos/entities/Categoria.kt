package com.dTeam.ciudadanos.entities

class Categoria(
    var id: String,
    var nombre : String,
    var description: String,
    var subcategorias : MutableList<Subcategoria>
) {

}
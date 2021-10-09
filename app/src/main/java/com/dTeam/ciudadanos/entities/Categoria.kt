package com.dTeam.ciudadanos.entities

class Categoria(
    var nombre : String,
    var subcategorias : MutableList<Subcategoria>? = null
)
{

}
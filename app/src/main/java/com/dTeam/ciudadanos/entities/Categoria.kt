package com.dTeam.ciudadanos.entities

import com.example.gestiondereclamos.entities.Subcategoria

class Categoria(
    var id: String,
    var nombre : String,
    var description: String,
    var subcategorias : MutableList<Subcategoria>
) {

}
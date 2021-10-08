package com.dTeam.ciudadanos.entities

import com.example.gestiondereclamos.entities.Subcategoria

class Categoria(
    var title : String,
    var description: String,
    var subcategorias : MutableList<Subcategoria>
    // var tipoReclamo : MutableList<TipoReclamo>
) {

}
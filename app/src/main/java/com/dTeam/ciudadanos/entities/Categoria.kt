package com.dTeam.ciudadanos.entities

class Categoria(
    nombre : String
) {
    var nombre: String
    var subcategorias: MutableList<Subcategoria>
    constructor() : this("")

    init {
        this.nombre = nombre
        this.subcategorias = mutableListOf()
    }
}
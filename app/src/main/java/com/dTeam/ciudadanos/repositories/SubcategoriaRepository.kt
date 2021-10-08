package com.dTeam.ciudadanos.repositories

import com.example.gestiondereclamos.entities.Subcategoria

class SubcategoriaRepository {


    private var reclamoList : MutableList<Subcategoria> = mutableListOf()

    init {
        reclamoList.add(Subcategoria("Reparación de luminaria"))
        reclamoList.add(Subcategoria("Extracción de árbol"))
        reclamoList.add(Subcategoria("Reparación de veredas"))
        reclamoList.add(Subcategoria("Vehículos abandonados"))
        reclamoList.add(Subcategoria("Mobiliario urbano"))
        reclamoList.add(Subcategoria("Reparación de baches"))
        reclamoList.add(Subcategoria("Rampas de accesibilidad"))
        reclamoList.add(Subcategoria("Rampas y fachadas"))
        reclamoList.add(Subcategoria("Cestos y contenedores"))
        reclamoList.add(Subcategoria("Publicidad en vía pública"))
        reclamoList.add(Subcategoria("Alcantarillas/sumideros"))
        reclamoList.add(Subcategoria("Calle anegada/inundada"))




    }

    fun getListaTipoReclamo() : MutableList<Subcategoria>{

        return reclamoList
    }
}
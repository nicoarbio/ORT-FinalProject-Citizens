package com.dTeam.ciudadanos.repositories

import com.dTeam.ciudadanos.entities.Categoria
import com.dTeam.ciudadanos.entities.Subcategoria

class CategoriaRepository {


    private var categoriaList : MutableList<Categoria> = mutableListOf()

    init {
        /*
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

         */




    }

    fun getListaTipoReclamo() : MutableList<Categoria>{

        return categoriaList
    }
}
package com.example.gestiondereclamos.entities

class TipoReclamoRepository {


    private var reclamoList : MutableList<TipoReclamo> = mutableListOf()

    init {
        reclamoList.add(TipoReclamo("Reparación de luminaria"))
        reclamoList.add(TipoReclamo("Extracción de árbol"))
        reclamoList.add(TipoReclamo("Reparación de veredas"))
        reclamoList.add(TipoReclamo("Vehículos abandonados"))
        reclamoList.add(TipoReclamo("Mobiliario urbano"))
        reclamoList.add(TipoReclamo("Reparación de baches"))
        reclamoList.add(TipoReclamo("Rampas de accesibilidad"))
        reclamoList.add(TipoReclamo("Rampas y fachadas"))
        reclamoList.add(TipoReclamo("Cestos y contenedores"))
        reclamoList.add(TipoReclamo("Publicidad en vía pública"))
        reclamoList.add(TipoReclamo("Alcantarillas/sumideros"))
        reclamoList.add(TipoReclamo("Calle anegada/inundada"))




    }

    fun getListaTipoReclamo() : MutableList<TipoReclamo>{

        return reclamoList
    }
}
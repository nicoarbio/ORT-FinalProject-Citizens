package com.dTeam.ciudadanos.repositories

import com.dTeam.ciudadanos.entities.Categoria
import com.dTeam.ciudadanos.entities.Reclamo

class ReclamoRepository (){

    private var reclamoList : MutableList<Reclamo> = mutableListOf()

    init {
        /*
        reclamoList.add(Categoria("Alumbrado","Alumbrado"))
        reclamoList.add(Categoria("Arbolado","Alumbrado"))
        reclamoList.add(Categoria("Calles y Veredas","Alumbrado"))
        reclamoList.add(Categoria("Control edilicio, obras y catastro","Alumbrado"))
        reclamoList.add(Categoria("Educación","Alumbrado"))
        reclamoList.add(Categoria("Limpieza y Recolección","Alumbrado"))
        reclamoList.add(Categoria("Ordenamiento del espacio público","Alumbrado"))
        reclamoList.add(Categoria("Parques y plazas","Alumbrado"))
        reclamoList.add(Categoria("Pluviales","Alumbrado"))
        reclamoList.add(Categoria("Seguridad","Alumbrado"))
        */



    }

    fun getCategoria() : MutableList<Reclamo>{

        return reclamoList
    }

    fun getDescription(pos:Int) : String{
        return reclamoList[pos].descripcion
    }

   // fun getImage(pos:Int) : String{
       // return reclamoList[pos].urlImage
  //  }
}
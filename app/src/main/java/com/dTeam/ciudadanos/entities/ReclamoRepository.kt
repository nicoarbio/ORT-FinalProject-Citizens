package com.example.gestiondereclamos.entities

import com.dTeam.ciudadanos.entities.Categoria

class ReclamoRepository (){

    private var reclamoList : MutableList<Categoria> = mutableListOf()

    init {
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




    }

    fun getCategoria() : MutableList<Categoria>{

        return reclamoList
    }

    fun getDescription(pos:Int) : String{
        return reclamoList[pos].description
    }

   // fun getImage(pos:Int) : String{
       // return reclamoList[pos].urlImage
  //  }
}
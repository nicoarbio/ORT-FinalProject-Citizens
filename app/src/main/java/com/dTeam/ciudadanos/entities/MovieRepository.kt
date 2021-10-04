package com.example.gestiondereclamos.entities

class MovieRepository (){

    private var reclamoList : MutableList<Reclamo> = mutableListOf()

    init {
        reclamoList.add(Reclamo("Alumbrado","Alumbrado"))
        reclamoList.add(Reclamo("Arbolado","Alumbrado"))
        reclamoList.add(Reclamo("Calles y Veredas","Alumbrado"))
        reclamoList.add(Reclamo("Control edilicio, obras y catastro","Alumbrado"))
        reclamoList.add(Reclamo("Educación","Alumbrado"))
        reclamoList.add(Reclamo("Limpieza y Recolección","Alumbrado"))
        reclamoList.add(Reclamo("Ordenamiento del espacio público","Alumbrado"))
        reclamoList.add(Reclamo("Parques y plazas","Alumbrado"))
        reclamoList.add(Reclamo("Pluviales","Alumbrado"))
        reclamoList.add(Reclamo("Seguridad","Alumbrado"))




    }

    fun getMovies() : MutableList<Reclamo>{

        return reclamoList
    }

    fun getDescription(pos:Int) : String{
        return reclamoList[pos].description
    }

   // fun getImage(pos:Int) : String{
       // return reclamoList[pos].urlImage
  //  }
}
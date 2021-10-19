package com.dTeam.ciudadanos.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.entities.Observacion

class ListaObservacionesAdaper (var listaObservaciones: MutableList<Observacion>) :
    RecyclerView.Adapter<ListaObservacionesAdaper.ObservacionHolder>(){

    class ObservacionHolder (v: View) : RecyclerView.ViewHolder(v){

        private var view: View
        init {
            this.view = v
        }



    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaObservacionesAdaper.ObservacionHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        holder: ListaObservacionesAdaper.ObservacionHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
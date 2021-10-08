package com.dTeam.ciudadanos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R

import com.dTeam.ciudadanos.entities.Subcategoria

class TipoReclamoAdapter(var reclamoList : MutableList <Subcategoria>,
                         var context : Context,
                         var onClick : (Int)->Unit) : RecyclerView.Adapter<TipoReclamoAdapter.TipoReclamoHolder>() {

    class TipoReclamoHolder (v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setTitle(title:String){
            var txtTitle : TextView = view.findViewById(R.id.txtTipoReclamo)
            txtTitle.text = title
        }

        fun getCardView() : CardView {
            return view.findViewById(R.id.cardTipoReclamo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoReclamoHolder {

        val view =  LayoutInflater.from(parent.context).inflate(R.layout.tipo_reclamo_item,parent,false)
        return (TipoReclamoHolder(view))
    }

    override fun onBindViewHolder(holder: TipoReclamoHolder, position: Int) {

        holder.setTitle(reclamoList[position].nombre)

        /*var cardImage : ImageView =  holder.getImageView()
        Glide.with(context)
            .load(reclamoList[position].urlImage)
            .into(cardImage)*/

        holder.getCardView().setOnClickListener(){
            onClick(position)
        }
    }

    override fun getItemCount(): Int {

        return reclamoList.size
    }
}
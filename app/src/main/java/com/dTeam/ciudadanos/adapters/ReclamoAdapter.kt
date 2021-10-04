package com.example.gestiondereclamos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Reclamos



class ReclamoAdapter (var reclamoList : MutableList <Reclamos>,
                      var context :Context,
                      var onClick : (Int)->Unit) : RecyclerView.Adapter<ReclamoAdapter.ReclamoHolder>() {

    class ReclamoHolder (v: View) : RecyclerView.ViewHolder(v) {

        private var view: View

        init {
            this.view = v
        }

        fun setTitle(title:String){
            var txtTitle : TextView = view.findViewById(R.id.txtTitleMovie)
            txtTitle.text = title
        }

        fun getCardView() : CardView {
            return view.findViewById(R.id.cardMovie)
        }
        fun getImageView() : ImageView{
         return  view.findViewById(R.id.cardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReclamoHolder {

        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return (ReclamoHolder(view))
    }

    override fun onBindViewHolder(holder: ReclamoHolder, position: Int) {

        holder.setTitle(reclamoList[position].title)

       /* var cardImage : ImageView =  holder.getImageView()
        Glide.with(context)
            .load(reclamoList[position].urlImage)
            .into(cardImage) */

        holder.getCardView().setOnClickListener(){
            onClick(position)
        }
    }

    override fun getItemCount(): Int {

        return reclamoList.size
    }
}
package com.dTeam.ciudadanos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.google.firebase.storage.FirebaseStorage

class ImgReclamoAdapter (var listaUrl: MutableList<String>, var context : Context) :
    RecyclerView.Adapter<ImgReclamoAdapter.ImgReclamoHolder>(){

    class ImgReclamoHolder (v: View) : RecyclerView.ViewHolder(v){
        private var view: View

        init {
            this.view = v
        }

        fun getCardView() : CardView {
            return view.findViewById(R.id.cardImgReclamo)
        }
        fun getImageReclamo() : ImageView {
            return  view.findViewById(R.id.imgItemReclamo)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImgReclamoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_img_reclamo,parent,false)
        return (ImgReclamoHolder(view))
    }

    override fun onBindViewHolder(holder: ImgReclamoHolder, position: Int) {
        val cardImageReclamo : ImageView =  holder.getImageReclamo()
        Glide.with(context)
            .load(listaUrl[position])
            .into(cardImageReclamo)
    }

    override fun getItemCount(): Int {
        return listaUrl.size
    }
}
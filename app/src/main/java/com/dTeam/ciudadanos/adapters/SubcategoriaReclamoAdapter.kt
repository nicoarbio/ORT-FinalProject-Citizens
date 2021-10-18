package com.dTeam.ciudadanos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Subcategoria
import com.dTeam.ciudadanos.fragments.FragmentNuevoReclamoDirections
import com.dTeam.ciudadanos.fragments.SubcategoriaReclamoListDirections
import com.google.firebase.storage.FirebaseStorage

class SubcategoriaReclamoAdapter(
    var subCategoriaList: MutableList<Subcategoria>,
    var context: Context,
    var onClick: (Int)->Unit) : RecyclerView.Adapter<SubcategoriaReclamoAdapter.SubcategoriaReclamoHolder>()
{
    lateinit var view: View
    class SubcategoriaReclamoHolder  (v: View) : RecyclerView.ViewHolder(v)
    {
        private var view: View

            init {
                this.view = v
            }

        fun setSubcategoria(nombre:String){
            var txtSubcategoria : TextView = view.findViewById(R.id.lblSubcategoriaReclamoItem)
            txtSubcategoria.text = nombre
        }
        fun getCardView() : CardView {
                return view.findViewById(R.id.cardTipoReclamo)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoriaReclamoAdapter.SubcategoriaReclamoHolder {

        view =  LayoutInflater.from(parent.context).inflate(R.layout.subcategoria_reclamo_item,parent,false)
        return (SubcategoriaReclamoAdapter.SubcategoriaReclamoHolder(view))
    }
    override fun onBindViewHolder(holder: SubcategoriaReclamoAdapter.SubcategoriaReclamoHolder, position: Int) {

        holder.setSubcategoria(subCategoriaList[position].nombre)

        holder.getCardView().setOnClickListener(){
            onClick(position)
            val action = SubcategoriaReclamoListDirections.actionSubcategoriaReclamoListToFragmentNuevoReclamo()
            view.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return subCategoriaList.size
    }

}


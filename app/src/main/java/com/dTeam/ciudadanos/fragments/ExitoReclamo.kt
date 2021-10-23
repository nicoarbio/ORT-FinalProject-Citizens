package com.dTeam.ciudadanos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dTeam.ciudadanos.R

import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel


class ExitoReclamo : Fragment() {

    companion object {
        fun newInstance() = ExitoReclamo()
    }


    private lateinit var viewModelReclamo: ReclamoViewModel

    lateinit var categoria : TextView
    lateinit var subCategoria : TextView
    lateinit var direccion : TextView
    lateinit var comentario : TextView
    lateinit var v : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.exito_reclamo_fragment, container, false)
        categoria = v.findViewById(R.id.txtCategoriaExito)
        subCategoria = v.findViewById(R.id.txtSubcategoriaExito)
        direccion = v.findViewById(R.id.txtDirecExito)
        comentario = v.findViewById(R.id.txtComentarioExito)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelReclamo = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)

        // TODO: Use the ViewModel

    }

    override fun onStart() {
        super.onStart()
        categoria.text = viewModelReclamo.getCategoria()
        subCategoria.text = viewModelReclamo.getSubcategoria()
        direccion.text = viewModelReclamo.getDireccion()
        comentario.text = viewModelReclamo.getDescripcion()
    }
}
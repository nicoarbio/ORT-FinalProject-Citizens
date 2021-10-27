package com.dTeam.ciudadanos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R

class InicioCiudadano : Fragment() {

    companion object {
        fun newInstance() = InicioCiudadano()
    }

    lateinit var v: View
    private lateinit var nuevoReclamo : Button
    private lateinit var misreclamos : Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        v =  inflater.inflate(R.layout.inicio_ciudadano_fragment, container, false)

        nuevoReclamo = v.findViewById(R.id.btnNuevoReclamo)
        nuevoReclamo.setOnClickListener{
            val action = InicioCiudadanoDirections.actionInicioCiudadanoToListaCategorias()
            v.findNavController().navigate(action)

        }


        misreclamos = v.findViewById(R.id.btnMisReclamos)
        misreclamos.setOnClickListener{
            val action2 =  InicioCiudadanoDirections.actionInicioCiudadanoToListaReclamos()
            v.findNavController().navigate(action2)

        }
        return v
    }



}
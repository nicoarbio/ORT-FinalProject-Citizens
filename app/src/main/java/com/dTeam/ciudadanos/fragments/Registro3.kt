package com.dTeam.ciudadanos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R

class Registro3 : Fragment() {

    companion object {
        fun newInstance() = Registro3()
    }

    lateinit var btnContinuar: Button
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.registro3_fragment, container, false)
        btnContinuar = v.findViewById(R.id.btnContinuar)
        btnContinuar.setOnClickListener{
            val actionToInicio = Registro3Directions.actionRegistro3ToLogIn()
            v.findNavController().navigate(actionToInicio)
        }

        return v
    }

}
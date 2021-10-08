package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.viewmodels.Registro1ViewModel

class Registro1 : Fragment() {

    companion object {
        fun newInstance() = Registro1()
    }

    private lateinit var viewModel: Registro1ViewModel
    lateinit var btnReg : Button
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.registro1_fragment, container, false)
        btnReg = v.findViewById(R.id.btnReg1)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Registro1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        btnReg.setOnClickListener {

            val action = Registro1Directions.actionRegistro1ToRegistro2()
            v.findNavController().navigate(action)
        }
    }
}
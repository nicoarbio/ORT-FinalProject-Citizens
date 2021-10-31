package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class LogIn : Fragment() {

    lateinit var v: View
    lateinit var btnRegistro : Button
    lateinit var txtUsuario : EditText
    lateinit var txtPassword : EditText
    private lateinit var usuarioViewModel: UsuarioViewModel
    companion object {
        fun newInstance() = LogIn()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.log_in_fragment, container, false)
        txtUsuario = v.findViewById(R.id.txtEmailLogin)
        txtPassword = v.findViewById(R.id.txtPasswordLogin)
        btnRegistro = v.findViewById(R.id.btnRegistrarUsuario)


        btnRegistro.setOnClickListener{
            val action =  LogInDirections.actionLogInToRegistro1()
            v.findNavController().navigate(action)
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
    }

}
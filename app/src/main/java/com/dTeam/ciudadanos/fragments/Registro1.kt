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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.ReclamoAdapter
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar

class Registro1 : Fragment() {

    companion object {
        fun newInstance() = Registro1()
    }

    lateinit var btnReg : Button
    lateinit var txtMail : EditText
    lateinit var txtPassword : EditText
    lateinit var txtConfirmarPass : EditText
    lateinit var txtDireccion : EditText
    private lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.registro1_fragment, container, false)
        btnReg = v.findViewById(R.id.btnReg1)
        txtMail =  v.findViewById(R.id.txtMailRegistro)
        txtPassword = v.findViewById(R.id.txtPasswordRegistro)
        txtConfirmarPass = v.findViewById(R.id.txtConfirmarPassword)
        txtDireccion =  v.findViewById(R.id.txtDireccionRegistro)

        btnReg.setOnClickListener{
            if(validarCampos(txtMail, txtPassword, txtConfirmarPass, txtDireccion)){
                if(txtPassword.text.toString() == txtConfirmarPass.text.toString()){
                    val action = Registro1Directions.actionRegistro1ToRegistro2(txtMail.text.toString(), txtPassword.text.toString(), txtDireccion.text.toString())
                    v.findNavController().navigate(action)
                }else{
                    Snackbar.make(v, "Las contraseñas no coinciden", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        return v
    }

    fun validarCampos(vararg campos:EditText):Boolean{
        var camposValidos = true
        for (campo in campos) {
            if(campo.text.isEmpty()){
                camposValidos = false
                campo.setError(getString(R.string.campoVacio))
            }
        }
        return camposValidos
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
    }
}
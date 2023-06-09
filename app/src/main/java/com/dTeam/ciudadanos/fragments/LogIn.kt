package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar

class LogIn : Fragment() {

    lateinit var v: View
    lateinit var btnRegistro : Button
    lateinit var btnLogin : Button
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
        btnLogin = v.findViewById(R.id.btnIniciarSesion)

        btnRegistro.setOnClickListener{
            val action =  LogInDirections.actionLogInToRegistro1()
            v.findNavController().navigate(action)
        }

        btnLogin.setOnClickListener{
            if(validarCampos(txtUsuario, txtPassword)){
                usuarioViewModel.iniciarSesion(txtUsuario.text.toString(), txtPassword.text.toString())
                usuarioViewModel.usuarioLogueadoOk.observe(viewLifecycleOwner, Observer { list ->
                    if (list){
                        val action = LogInDirections.actionLogInToInicioCiudadano()
                        v.findNavController().navigate(action)
                    }
                    else{
                        if(!usuarioViewModel.error.isEmpty()) {
                            Snackbar.make(v, usuarioViewModel.error, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                })
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
        if(usuarioViewModel.obtenerUsuarioLogueado()!=null){
            var action = LogInDirections.actionLogInToInicioCiudadano()
            v.findNavController().navigate(action)
        }
    }

}
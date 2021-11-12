package com.dTeam.ciudadanos.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.callbackFlow

class Registro2 : Fragment() {

    companion object {
        fun newInstance() = Registro2()
    }

    lateinit var btnReg : Button
    lateinit var txtNombre : EditText
    lateinit var txtApellido : EditText
    lateinit var txtFechaNac : EditText
    lateinit var txtDni : EditText
    lateinit var txtTelefono : EditText
    private lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.registro2_fragment, container, false)

        btnReg = v.findViewById(R.id.btnReg2)

        txtNombre =  v.findViewById(R.id.editNombre)
        txtApellido =  v.findViewById(R.id.editApellido)
        txtFechaNac =  v.findViewById(R.id.editFechaNac)
        txtDni =  v.findViewById(R.id.editDni)
        txtTelefono =  v.findViewById(R.id.editTelefono)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnReg.setOnClickListener {
            var user = Usuario()
            user.type = "Usuario"
            user.rol = "Ciudadano"
            user.nombre = txtNombre.text.toString()
            user.apellido = txtApellido.text.toString()
            user.fechaDeNacimiento = txtFechaNac.text.toString()
            user.dni = txtDni.text.toString()
            user.telefono = txtTelefono.text.toString()
            user.email = Registro2Args.fromBundle(requireArguments()).email
            user.direccion = Registro2Args.fromBundle(requireArguments()).address
            usuarioViewModel.registrarUsuario(user, Registro2Args.fromBundle(requireArguments()).password)
            usuarioViewModel.usuarioRegistadoOk.observe(viewLifecycleOwner, Observer {
                if (usuarioViewModel.usuarioRegistadoOk.value == true){
                    val action = Registro2Directions.actionRegistro2ToRegistro3()
                    v.findNavController().navigate(action)
                } else {
                    Snackbar.make(v, usuarioViewModel.error, Snackbar.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack() //Bugg!
                }
            })
        }
    }

}
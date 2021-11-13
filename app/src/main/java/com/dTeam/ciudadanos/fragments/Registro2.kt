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
            var user = Usuario(
                "Usuario",
                "Ciudadano",
                txtNombre.text.toString(),
                txtApellido.text.toString(),
                txtFechaNac.text.toString(),
                txtDni.text.toString(),
                txtTelefono.text.toString(),
                Registro2Args.fromBundle(requireArguments()).email,
                Registro2Args.fromBundle(requireArguments()).address
            )

            usuarioViewModel.registrarUsuario(user, Registro2Args.fromBundle(requireArguments()).password)

            usuarioViewModel.usuarioRegistadoOk.observe(viewLifecycleOwner, Observer {
                //Cuando el registro falla al primer intento, cuando un segundo intento legal se ejecuta, se observa
                // nuevamente que el Ok está en falso, lo que no permite la correcta circulación en la aplicación.
                if (usuarioViewModel.usuarioRegistadoOk.value == true){
                    val action = Registro2Directions.actionRegistro2ToRegistro3()
                    v.findNavController().navigate(action)
                } else {
                    Snackbar.make(v, usuarioViewModel.error, Snackbar.LENGTH_SHORT).show()
                    //parentFragmentManager.popBackStack()
                    //val action = Registro2Directions.actionRegistro2ToRegistro1()
                    v.findNavController().navigate(R.id.action_registro2_to_registro1)
                }
            })
        }
    }

}
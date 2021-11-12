package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.ImgReclamoAdapter
import com.dTeam.ciudadanos.adapters.ListaObservacionesAdaper
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel

class PerfilUsuario : Fragment() {

    companion object {
        fun newInstance() = PerfilUsuario()
    }
    lateinit var v: View
    private lateinit var usuarioViewModel: UsuarioViewModel

    private lateinit var txtNombreApellido : TextView
    private lateinit var txtEmail : TextView
    private lateinit var txtTelefono : TextView
    private lateinit var txtDireccion : TextView
    private lateinit var txtDni : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.perfil_usuario_fragment, container, false)
        txtEmail = v.findViewById(R.id.lblEmailPerfil)
        txtNombreApellido = v.findViewById(R.id.lblNombreYApellidoPerfil)
        txtDni = v.findViewById(R.id.lblDni)
        txtDireccion = v.findViewById(R.id.lblDireccionPerfil)
        txtTelefono = v.findViewById(R.id.lblTelefonoPerfil)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        usuarioViewModel.actualizarUsuarioLogueado()
        setObserver()
    }
    fun setObserver(){
        usuarioViewModel.usuario.observe(viewLifecycleOwner, Observer {

            txtNombreApellido.text = it.nombre +" "+it.apellido
            txtDireccion.text = it.direccion
            txtTelefono.text = it.telefono
            txtDni.text = it.dni
            txtEmail.text = it.email
        })
    }

}
package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel

class PerfilUsuario : Fragment() {

    companion object {
        fun newInstance() = PerfilUsuario()
    }

    private lateinit var viewModel: UsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perfil_usuario_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsuarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
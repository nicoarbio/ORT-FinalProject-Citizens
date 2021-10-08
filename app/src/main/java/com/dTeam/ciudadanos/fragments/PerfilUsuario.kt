package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.viewmodels.PerfilUsuarioViewModel

class PerfilUsuario : Fragment() {

    companion object {
        fun newInstance() = PerfilUsuario()
    }

    private lateinit var viewModel: PerfilUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perfil_usuario_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PerfilUsuarioViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
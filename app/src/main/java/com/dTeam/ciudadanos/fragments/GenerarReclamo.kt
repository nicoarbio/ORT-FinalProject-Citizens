package com.example.gestiondereclamos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dTeam.ciudadanos.R
import com.example.gestiondereclamos.viewmodels.GenerarReclamoViewModel

class GenerarReclamo : Fragment() {

    companion object {
        fun newInstance() = GenerarReclamo()
    }

    private lateinit var viewModel: GenerarReclamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.generar_reclamo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GenerarReclamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
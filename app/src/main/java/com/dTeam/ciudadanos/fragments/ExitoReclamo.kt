package com.example.gestiondereclamos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dTeam.ciudadanos.R
import com.example.gestiondereclamos.viewmodels.ExitoReclamoViewModel

class ExitoReclamo : Fragment() {

    companion object {
        fun newInstance() = ExitoReclamo()
    }

    private lateinit var viewModel: ExitoReclamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exito_reclamo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExitoReclamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
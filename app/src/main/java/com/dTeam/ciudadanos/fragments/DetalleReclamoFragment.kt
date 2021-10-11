package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.viewmodels.DetalleReclamoViewModel

class DetalleReclamoFragment : Fragment() {

    companion object {
        fun newInstance() = DetalleReclamoFragment()
    }

    private lateinit var viewModel: DetalleReclamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalle_reclamo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleReclamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
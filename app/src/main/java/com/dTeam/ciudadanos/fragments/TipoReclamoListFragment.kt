package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.TipoReclamoAdapter
import com.dTeam.ciudadanos.repositories.CategoriaRepository
import com.dTeam.ciudadanos.viewmodels.TipoReclamoListViewModel

class TipoReclamoListFragment : Fragment() {

    companion object {
        fun newInstance() = TipoReclamoListFragment()
    }

    private lateinit var viewModel: TipoReclamoListViewModel
    private lateinit var v : View
    private lateinit var recTipoReclamo: RecyclerView
    private var tipoReclamoList = CategoriaRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.tipo_reclamo_list_fragment, container, false)
        recTipoReclamo = v.findViewById(R.id.tipoReclamoRecycler)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TipoReclamoListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        recTipoReclamo.setHasFixedSize(true)
        recTipoReclamo.layoutManager = LinearLayoutManager(context)
        recTipoReclamo.adapter = TipoReclamoAdapter(tipoReclamoList.getListaTipoReclamo(), requireContext()) { x ->
            // onItemClick(x)
        }


    }
}

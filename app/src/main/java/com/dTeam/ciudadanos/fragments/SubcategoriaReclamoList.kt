package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.SubcategoriaReclamoAdapter
import com.dTeam.ciudadanos.viewmodels.CategoriaViewModel
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.android.material.snackbar.Snackbar

class SubcategoriaReclamoList : Fragment() {

    companion object {
        fun newInstance() = SubcategoriaReclamoList()
    }

    private lateinit var viewModel: CategoriaViewModel
    private lateinit var reclamoViewModel: ReclamoViewModel
    private lateinit var v : View
    private lateinit var listadoSubcategorias: RecyclerView
    private lateinit var subCategoriasAdapter: SubcategoriaReclamoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.subcategoria_reclamo_list_fragment, container, false)
        listadoSubcategorias = v.findViewById(R.id.listadoSubcategorias)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CategoriaViewModel::class.java)
        reclamoViewModel = ViewModelProvider(this).get(ReclamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart()
    {
        super.onStart()
        listadoSubcategorias.setHasFixedSize(true)
        listadoSubcategorias.layoutManager = LinearLayoutManager(context)
        viewModel.getSubcategorias()
        subCategoriasAdapter = SubcategoriaReclamoAdapter(mutableListOf(), requireContext()) { pos -> onItemClick(pos)}
        setObserver()
    }
    fun setObserver(){
        viewModel.listadoSubcategoria.observe(viewLifecycleOwner, Observer {list ->
            subCategoriasAdapter = SubcategoriaReclamoAdapter(list, requireContext()) { pos -> onItemClick(pos) }
            listadoSubcategorias.adapter = subCategoriasAdapter
        })
    }

    fun onItemClick(pos: Int){
        Snackbar.make(v,pos.toString(), Snackbar.LENGTH_SHORT).show()
    }

}
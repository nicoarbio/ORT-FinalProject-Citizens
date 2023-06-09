package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.SubcategoriaReclamoAdapter
import com.dTeam.ciudadanos.viewmodels.CategoriaViewModel
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel

class SubcategoriaReclamoList : Fragment() {

    companion object {
        fun newInstance() = SubcategoriaReclamoList()
    }

    private lateinit var categoriaViewModel: CategoriaViewModel
    private lateinit var reclamoViewModel: ReclamoViewModel
    private lateinit var v : View
    private lateinit var listadoSubcategorias: RecyclerView
    private lateinit var subCategoriasAdapter: SubcategoriaReclamoAdapter

    private lateinit var progresBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.subcategoria_reclamo_list_fragment, container, false)
        listadoSubcategorias = v.findViewById(R.id.listadoSubcategorias)
        progresBar = v.findViewById(R.id.progressBarSubcateg)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoriaViewModel = ViewModelProvider(requireActivity()).get(CategoriaViewModel::class.java)
        reclamoViewModel = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)
    }

    override fun onStart()
    {
        super.onStart()
        listadoSubcategorias.setHasFixedSize(true)
        listadoSubcategorias.layoutManager = LinearLayoutManager(context)
        categoriaViewModel.getSubcategorias()
        subCategoriasAdapter = SubcategoriaReclamoAdapter(mutableListOf(), requireContext()) { pos -> onItemClick(pos)}
        setObserver()
    }

    override fun onResume() {
        super.onResume()
        progresBar.visibility = View.VISIBLE
    }

    fun setObserver(){
        categoriaViewModel.listadoSubcategoria.observe(viewLifecycleOwner, Observer { list ->
            subCategoriasAdapter = SubcategoriaReclamoAdapter(list, requireContext()) { pos -> onItemClick(pos) }
            listadoSubcategorias.adapter = subCategoriasAdapter
            progresBar.visibility = View.INVISIBLE
        })
    }

    fun onItemClick(pos: Int){
        val subCategoria = categoriaViewModel.listadoSubcategoria.value?.get(pos)?.nombre
        reclamoViewModel.setSubcategoria(subCategoria!!)
        val action = SubcategoriaReclamoListDirections.actionSubcategoriaReclamoListToFragmentNuevoReclamo()
        v.findNavController().navigate(action)
    }

}
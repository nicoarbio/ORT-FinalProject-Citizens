package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.CategoriaReclamoAdapter
import com.dTeam.ciudadanos.adapters.ReclamoAdapter
import com.dTeam.ciudadanos.viewmodels.CategoriaViewModel
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.android.material.snackbar.Snackbar

class TipoReclamoListFragment : Fragment() {

    companion object {
        fun newInstance() = TipoReclamoListFragment()
    }

    private lateinit var viewModel: CategoriaViewModel
    private lateinit var reclamoViewModel: ReclamoViewModel
    private lateinit var v : View
    private lateinit var listadoCategorias: RecyclerView
    private lateinit var categoriasAdapter: CategoriaReclamoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.tipo_reclamo_list_fragment, container, false)
        listadoCategorias = v.findViewById(R.id.listadoCategorias)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriaViewModel::class.java)
        reclamoViewModel = ViewModelProvider(this).get(ReclamoViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        listadoCategorias.setHasFixedSize(true)
        listadoCategorias.layoutManager = GridLayoutManager(context, 3)
        viewModel.getCategorias()
        categoriasAdapter = CategoriaReclamoAdapter(mutableListOf(), requireContext()) { pos -> onItemClick(pos)}
        setObserver()
    }

    fun setObserver(){
        viewModel.listadoCategorias.observe(viewLifecycleOwner, Observer {list ->
            categoriasAdapter = CategoriaReclamoAdapter(list, requireContext()) { pos -> onItemClick(pos) }
            listadoCategorias.adapter = categoriasAdapter
        })
    }

    fun onItemClick(pos: Int){
        /*val action2 = ReclamoListFragmentDirections.actionMovieListFragmentToLandFragment(repository.getDescription(pos))
        v.findNavController().navigate(action2)*/
        Snackbar.make(v,pos, Snackbar.LENGTH_SHORT).show()
    }
}

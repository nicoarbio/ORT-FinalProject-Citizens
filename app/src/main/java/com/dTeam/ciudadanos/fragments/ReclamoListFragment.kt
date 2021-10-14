package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R

import com.dTeam.ciudadanos.adapters.ReclamoAdapter
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.repositories.ReclamoRepository

import com.dTeam.ciudadanos.viewmodels.ReclamoListViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ReclamoListFragment : Fragment() {

    companion object {
        fun newInstance() = ReclamoListFragment()
    }

    private lateinit var viewModel: ReclamoListViewModel

    private lateinit var v: View

    private var repository = ReclamoRepository()

    private lateinit var listadoReclamos: RecyclerView

    private lateinit var reclamoAdapter: ReclamoAdapter

    private var reclamoList : MutableList<Reclamo> = mutableListOf()
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.reclamo_list_fragment, container, false)
        listadoReclamos = v.findViewById(R.id.listadoReclamos)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReclamoListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        listadoReclamos.setHasFixedSize(true)
        listadoReclamos.layoutManager = LinearLayoutManager(context)
        reclamoAdapter = ReclamoAdapter(getReclamos(), requireContext()) { pos -> onItemClick(pos)  }
        listadoReclamos.adapter = reclamoAdapter
    }
    fun getReclamos(): MutableList<Reclamo> {
        reclamoList.clear()
        db.collection("reclamos")
            .whereEqualTo("usuario", "fperchuk@hotmail.com") //TODO: Acá poner el mail del usuario logueado!
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (reclamo in snapshot) {
                        reclamoList.add(reclamo.toObject())
                    }
                }
                reclamoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("Test", "Error al obtener documentos: ", exception)
            }
        return reclamoList
    }
    fun onItemClick(pos: Int){
        /*val action2 = ReclamoListFragmentDirections.actionMovieListFragmentToLandFragment(repository.getDescription(pos))
        v.findNavController().navigate(action2)*/
        Snackbar.make(v,pos.toString(), Snackbar.LENGTH_SHORT).show()
    }

}
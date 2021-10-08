package com.example.gestiondereclamos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dTeam.ciudadanos.R

import com.example.gestiondereclamos.adapters.ReclamoAdapter
import com.dTeam.ciudadanos.repositories.ReclamoRepository

import com.example.gestiondereclamos.viewmodels.MovieListViewModel

class ReclamoListFragment : Fragment() {

    companion object {
        fun newInstance() = ReclamoListFragment()
    }

    private lateinit var viewModel: MovieListViewModel

    private lateinit var v: View

    private var repository = ReclamoRepository()

    private lateinit var recMovie: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.reclamo_list_fragment, container, false)
        recMovie = v.findViewById(R.id.recMovie)



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()

        recMovie.setHasFixedSize(true)
        recMovie.layoutManager = GridLayoutManager(context,2)
        recMovie.adapter = ReclamoAdapter(repository.getCategoria(), requireContext()) { x ->
            // onItemClick(x)
        }


    }
}
    /*fun onItemClick(pos: Int){
        val action2 = ReclamoListFragmentDirections.actionMovieListFragmentToLandFragment(repository.getDescription(pos))
        v.findNavController().navigate(action2)
    }/*

}
}
     */
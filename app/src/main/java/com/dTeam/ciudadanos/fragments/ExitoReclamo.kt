package com.dTeam.ciudadanos.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.ReclamoAdapter

import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.firebase.storage.FirebaseStorage


class ExitoReclamo : Fragment() {

    companion object {
        fun newInstance() = ExitoReclamo()
    }


    private lateinit var viewModelReclamo: ReclamoViewModel

    lateinit var categoria : TextView
    lateinit var subCategoria : TextView
    lateinit var direccion : TextView
    lateinit var comentario : TextView
    lateinit var btnVolver : Button
    lateinit var imgDetalle: ImageView
    lateinit var v : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.exito_reclamo_fragment, container, false)
        categoria = v.findViewById(R.id.txtCategoriaExito)
        subCategoria = v.findViewById(R.id.txtSubcategoriaExito)
        direccion = v.findViewById(R.id.txtDirecExito)
        comentario = v.findViewById(R.id.txtComentarioExito)
        btnVolver = v.findViewById(R.id.btnVolver)
        imgDetalle = v.findViewById(R.id.imgDetalle)
        btnVolver.setOnClickListener{
            val actionToInicio = ExitoReclamoDirections.actionExitoReclamoToLogIn()
            v.findNavController().navigate(actionToInicio)
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = ExitoReclamoDirections.actionExitoReclamoToLogIn()
                v.findNavController().navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelReclamo = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        categoria.text = viewModelReclamo.getCategoria()
        subCategoria.text = viewModelReclamo.getSubcategoria()
        direccion.text = viewModelReclamo.getDireccion()
        comentario.text = viewModelReclamo.getDescripcion()
        val storage = FirebaseStorage.getInstance()// Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("gs://ort-proyectofinal.appspot.com/")
        val imgReclamo = gsReference.child("categorias").child(viewModelReclamo.getCategoria() + ".png")
        Glide.with(this)
            .load(imgReclamo)
            .into(imgDetalle)
        setObserver()
    }

    fun setObserver(){
        viewModelReclamo.reclamo.observe(viewLifecycleOwner, Observer { list ->
            categoria.text = viewModelReclamo.getCategoria()
            subCategoria.text = viewModelReclamo.getSubcategoria()
            direccion.text = viewModelReclamo.getDireccion()
            comentario.text = viewModelReclamo.getDescripcion()
        })
    }
}
package com.dTeam.ciudadanos.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.viewmodels.DetalleReclamoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage

class DetalleReclamoFragment : Fragment() {

    companion object {
        fun newInstance() = DetalleReclamoFragment()
    }

    private lateinit var viewModel: DetalleReclamoViewModel

    lateinit var v: View
    private lateinit var reclamo: Reclamo
    private lateinit var imgDetalleCategoria: ImageView
    private lateinit var txtDetalleCategoria: TextView
    private lateinit var txtDetalleSubCategoria: TextView

    private lateinit var txtDetalleDireccion: TextView
    private lateinit var txtDetalleComentario: TextView

    private lateinit var txtEstadoReclamo: TextView

    private lateinit var recDetalleObservaciones: RecyclerView

    private lateinit var btnDetalleAgregarObser: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.detalle_reclamo_fragment, container, false)
        //Imagen tipo reclamo
        imgDetalleCategoria = v.findViewById(R.id.imgDetalleCategoria)
        //Titulo
        txtDetalleCategoria = v.findViewById(R.id.txtDetalleCategoria)
        txtDetalleSubCategoria = v.findViewById(R.id.txtDetalleSubCategoria)

        txtDetalleDireccion = v.findViewById(R.id.txtDetalleDireccion)
        txtDetalleComentario = v.findViewById(R.id.txtDetalleComentario)

        txtEstadoReclamo = v.findViewById(R.id.txtEstadoReclamo)

        recDetalleObservaciones = v.findViewById(R.id.recDetalleObservaciones)

        btnDetalleAgregarObser = v.findViewById(R.id.btnDetalleAgregarObser)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetalleReclamoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        reclamo = DetalleReclamoFragmentArgs.fromBundle(requireArguments()).reclamo

        /*val storage = FirebaseStorage.getInstance()// Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("gs://ort-proyectofinal.appspot.com/")
        val imgReclamo = gsReference.child("categorias").child(reclamo.categoria + ".png")
        Glide.with(this)
            .load(imgReclamo)
            .into(imgDetalleCategoria)*/

        txtDetalleCategoria.text = reclamo.categoria
        txtDetalleSubCategoria.text = reclamo.subCategoria
        txtDetalleDireccion.text = reclamo.direccion
        txtDetalleComentario.text = reclamo.descripcion

        txtEstadoReclamo.text = reclamo.estado

    }


}
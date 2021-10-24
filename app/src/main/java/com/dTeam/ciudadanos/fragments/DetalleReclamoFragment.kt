package com.dTeam.ciudadanos.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.ListaObservacionesAdaper
import com.dTeam.ciudadanos.entities.Observacion
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetalleReclamoFragment : Fragment() {

    companion object {
        fun newInstance() = DetalleReclamoFragment()
    }

    private lateinit var reclamoViewModel: ReclamoViewModel

    lateinit var v: View
    private lateinit var imgDetalleCategoria: ImageView
    private lateinit var txtDetalleCategoria: TextView
    private lateinit var txtDetalleSubCategoria: TextView

    private lateinit var txtDetalleDireccion: TextView
    private lateinit var txtDetalleComentario: TextView

    private lateinit var txtEstadoReclamo: TextView

    private lateinit var recDetalleObservaciones: RecyclerView
    private lateinit var ObservacionAdapter: ListaObservacionesAdaper

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
        btnDetalleAgregarObser.setOnClickListener{
            showdialog()
        }



        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reclamoViewModel = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        val storage = FirebaseStorage.getInstance()// Create a reference to a file from a Google Cloud Storage URI
        val gsReference = storage.getReferenceFromUrl("gs://ort-proyectofinal.appspot.com/")
        val imgReclamo = gsReference.child("categorias").child(reclamoViewModel.getCategoria() + ".png")
        Glide.with(this)
            .load(imgReclamo)
            .into(imgDetalleCategoria)

        recDetalleObservaciones.setHasFixedSize(true)
        recDetalleObservaciones.layoutManager = LinearLayoutManager(context)
        //recDetalleObservaciones.adapter = ListaObservacionesAdaper(reclamoViewModel.getObservaciones()!!)
        txtDetalleCategoria.text = reclamoViewModel.getCategoria()
        txtDetalleSubCategoria.text = reclamoViewModel.getSubcategoria()
        txtDetalleDireccion.text = reclamoViewModel.getDireccion()
        txtDetalleComentario.text = reclamoViewModel.getDescripcion()
        txtEstadoReclamo.text = reclamoViewModel.getEstado()
    }

    fun showdialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
        builder.setTitle("Agregar Observación")

        val input = EditText(this.context)
        input.setHint("Ingrese su Observación")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Agregar", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            var texto = input.text.toString()
            val currentDateTime = LocalDateTime.now()
            val fecha = currentDateTime.format(DateTimeFormatter.ISO_DATE)
            var obsNuevo = Observacion("usuario", texto, fecha)
            if (texto.length > 0 && reclamoViewModel.agregarObser(obsNuevo)) {
                //obs generado con exito
                Snackbar.make(v,"se agregó la observación", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(v,"Ocurrió un error. Vuelva a intentar mas tarde", Snackbar.LENGTH_SHORT).show()
            }
        })
        builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()

    }


}
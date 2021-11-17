package com.dTeam.ciudadanos.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
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
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.adapters.ImgReclamoAdapter
import com.dTeam.ciudadanos.adapters.ListaObservacionesAdaper
import com.dTeam.ciudadanos.adapters.SubcategoriaReclamoAdapter
import com.dTeam.ciudadanos.entities.Observacion
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import android.widget.RelativeLayout

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Window


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

    private lateinit var recImgReclamo: RecyclerView

    private lateinit var ImgAdaper: ImgReclamoAdapter

    private lateinit var recDetalleObservaciones: RecyclerView
    private lateinit var ObservacionAdapter: ListaObservacionesAdaper

    private lateinit var btnDetalleAgregarObser: Button

    private lateinit var lblImg: TextView

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

        recImgReclamo = v.findViewById(R.id.recImgReclamo)
        recDetalleObservaciones = v.findViewById(R.id.recDetalleObservaciones)

        btnDetalleAgregarObser = v.findViewById(R.id.btnDetalleAgregarObser)
        btnDetalleAgregarObser.setOnClickListener{
            showdialog()
        }

        lblImg = v.findViewById(R.id.lblDetalleImgAdjuntas)

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


        recImgReclamo.setHasFixedSize(true)
        recImgReclamo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recDetalleObservaciones.setHasFixedSize(true)
        recDetalleObservaciones.layoutManager = LinearLayoutManager(context)

        recImgReclamo.adapter = ImgReclamoAdapter(mutableListOf(), requireContext()) { pos -> onItemClick(pos)}

        setObserver()

    }

    fun setObserver(){
        reclamoViewModel.reclamo.observe(viewLifecycleOwner, Observer {
            txtDetalleCategoria.text = it.categoria
            txtDetalleSubCategoria.text = it.subCategoria
            txtDetalleDireccion.text = it.direccion
            txtDetalleComentario.text = it.descripcion
            txtEstadoReclamo.text = it.estado
            recDetalleObservaciones.adapter = ListaObservacionesAdaper(it.observaciones)
            recImgReclamo.adapter = ImgReclamoAdapter(it.imagenes, requireContext()) { pos -> onItemClick(pos)}

            reclamoViewModel.getImgEstado()
            reclamoViewModel.imgEstadoReclamo.observe(viewLifecycleOwner, Observer {
                var imgEstado : ImageView =  v.findViewById(R.id.imgEstadoDetalleReclamo)
                Glide.with(this)
                    .load(it)
                    .into(imgEstado)
            })



            if( it.estado == "Cancelado" || it.estado == "Cerrado"){
                btnDetalleAgregarObser.visibility = View.GONE
            }

            if(it.imagenes.size ==0){
                lblImg.visibility = View.GONE
                recImgReclamo.visibility = View.GONE
            }
        })
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
            var obsNuevo = Observacion("Ciudadano", texto, fecha)

            if (input.length() > 0) {
                reclamoViewModel.agregarObser(obsNuevo)
                reclamoViewModel.estadoGuardadoOk.observe(viewLifecycleOwner, Observer{list ->
                    if(reclamoViewModel.estadoGuardadoOk.value==true){
                        recDetalleObservaciones.adapter = ListaObservacionesAdaper(reclamoViewModel.getObservaciones()!!)
                        Snackbar.make(v,"se agregó la observación", Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(v,R.string.errorGeneral, Snackbar.LENGTH_SHORT).show()
                    }
                })
            }else{
                Snackbar.make(v,"No se puede agregar una observación vacía", Snackbar.LENGTH_SHORT).show()
            }
        })
        builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()

    }

    fun onItemClick(pos: Int){
        showImage(reclamoViewModel.reclamo.value!!.imagenes[pos])
    }

    fun showImage(imageUriSting : String) {
        val builder = Dialog(this.requireContext(), android.R.style.Theme_Light)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(
            ColorDrawable(Color.BLACK)
        )
        builder.setOnDismissListener(DialogInterface.OnDismissListener {
            //nothing;
        })
        val imageView = ImageView(this.requireContext())
        var imageUri = Uri.parse(imageUriSting)
        Glide.with(this)
            .load(imageUri)
            .into(imageView)
        builder.addContentView(
            imageView, RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        )
        builder.show()
    }


}
package com.dTeam.ciudadanos.fragments
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import androidx.activity.OnBackPressedCallback

import androidx.annotation.RestrictTo

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class FragmentNuevoReclamo:Fragment() {
    lateinit var v: View
    lateinit var btnGenerarReclamo : Button
    lateinit var txtDescripcion : EditText
    lateinit var txtDireccion : EditText
    private lateinit var reclamoViewModel: ReclamoViewModel
    private lateinit var lblCategoriaReclamo : TextView
    private lateinit var lblSubcategoriaReclamo : TextView
    private lateinit var imgReclamo : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.nuevo_reclamo, container, false)
        txtDescripcion=v.findViewById(R.id.txtDescripcion)
        txtDireccion=v.findViewById(R.id.txtDireccion)
        btnGenerarReclamo = v.findViewById(R.id.btnGenerarReclamo)
        btnGenerarReclamo.setOnClickListener{
            var action : NavDirections

           val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmar reclamo")
            builder.setMessage("¿Desea confirmar el reclamo?")
            builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
                var reclamo = Reclamo(
                    reclamoViewModel.getCategoria()!!,
                    reclamoViewModel.getSubcategoria()!!,
                    txtDireccion.text.toString(),
                    txtDescripcion.text.toString(),
                    "UID_DEL_USUARIO", //TODO: Acá poner el UID del usuario logueado
                    "Abierto",
                    ""
                )

                if (reclamoViewModel.generarReclamo(reclamo)) {
                    //Reclamo generado con exito


                } else {
                    action =
                        FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToExitoReclamo()
                    v.findNavController().navigate(action)
                    Snackbar.make(
                        v,
                        "Ocurrió un error. Vuelva a intentar mas tarde",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            }
            builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

            }
            builder.show()

        }
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmar reclamo")
                builder.setMessage("¿Desea confirmar el reclamo?")
                builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
                    val action =
                        FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToInicioCiudadano()
                    v.findNavController().navigate(action)
                }
                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

                }
                builder.show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return v
    }

    override fun onStart() {
        super.onStart()
        lblCategoriaReclamo = v.findViewById(R.id.lbltipoReclamoNuevoReclamo)
        lblSubcategoriaReclamo = v.findViewById(R.id.lblSubtipoReclamoNuevoReclamo)
        imgReclamo = v.findViewById(R.id.imgReclamo) //TODO: Falta cargar img. Para esto habría que pasar la referencia a storage al viewmodel (idem para el código del adapter)
        lblCategoriaReclamo.text = reclamoViewModel.getCategoria()
        lblSubcategoriaReclamo.text = reclamoViewModel.getSubcategoria()


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reclamoViewModel = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)
    }
}

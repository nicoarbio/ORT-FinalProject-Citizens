package com.dTeam.ciudadanos.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Reclamo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentNuevoReclamo:Fragment() {
    lateinit var v: View
    lateinit var btnGenerarReclamo : Button
    lateinit var txtDescripcion : EditText
    val db = Firebase.firestore
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.nuevo_reclamo, container, false)
        txtDescripcion=v.findViewById(R.id.txtDescripcion)
        btnGenerarReclamo = v.findViewById(R.id.btnGenerarReclamo)
        btnGenerarReclamo.setOnClickListener{
            generarReclamo()
            val action = FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToExitoReclamo()
            v.findNavController().navigate(action)
        }
        return v
    }
    fun generarReclamo(){
        var reclamo : Reclamo = Reclamo("Arbolado","Extracción de árbol","Av. Siempre Viva 123",txtDescripcion.text.toString(),"fperchuk@hotmail.com", "Cerrado", "Pepito")
        db.collection("reclamos").add(reclamo)
            .addOnSuccessListener { Log.d("testNuevoReclamo", "TODO OK") }
            .addOnFailureListener{e -> Log.d("testNuevoReclamo", "ERROR: ", e)}
    }
}
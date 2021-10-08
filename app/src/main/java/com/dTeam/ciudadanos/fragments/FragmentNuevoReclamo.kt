package com.dTeam.ciudadanos.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
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
        }
        return v
    }
    fun generarReclamo(){
        var reclamo : Reclamo = Reclamo("2I1GoceDwcsznRHXL3XE","79SJTIyIygDWrgwcFMnv","Av. Siempre Viva 123",txtDescripcion.text.toString(),"prueba@gmail.com")
        db.collection("reclamos").add(reclamo)
    }
}
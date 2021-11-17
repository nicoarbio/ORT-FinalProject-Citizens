package com.dTeam.ciudadanos.fragments

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.network.OrionApi
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.material.snackbar.Snackbar
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import java.util.*

class Registro2 : Fragment() {

    companion object {
        fun newInstance() = Registro2()
    }

    lateinit var btnReg : Button
    lateinit var btnSubirFoto : Button
    lateinit var txtNombre : EditText
    lateinit var txtApellido : EditText
    lateinit var txtFechaNac : EditText
    lateinit var txtDni : EditText
    lateinit var txtTelefono : EditText
    var imgUsuario : Uri = Uri.EMPTY

    private lateinit var usuarioViewModel: UsuarioViewModel
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v= inflater.inflate(R.layout.registro2_fragment, container, false)

        btnReg = v.findViewById(R.id.btnReg2)
        btnSubirFoto = v.findViewById(R.id.btnSubirFotoUsuario)

        txtNombre =  v.findViewById(R.id.editNombre)
        txtApellido =  v.findViewById(R.id.editApellido)
        txtFechaNac =  v.findViewById(R.id.txtFechaNac)
        txtDni =  v.findViewById(R.id.editDni)
        txtTelefono =  v.findViewById(R.id.editTelefono)


        val fechaNacimiento : EditText = v.findViewById(R.id.txtFechaNac)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var mes = monthOfYear + 1
            fechaNacimiento.setText("" + dayOfMonth + "/" + mes + "/" + year)
        }, year, month, day)

        fechaNacimiento.setOnClickListener {
            dpd.show()
        }

        btnSubirFoto.setOnClickListener{

            TedImagePicker.with(requireContext())
                .start { uri -> imgUsuario = uri}
        }
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnReg.setOnClickListener {
            var user = Usuario(
                "Usuario",
                "Ciudadano",
                txtNombre.text.toString(),
                txtApellido.text.toString(),
                txtFechaNac.text.toString(),
                txtDni.text.toString(),
                txtTelefono.text.toString(),
                Registro2Args.fromBundle(requireArguments()).email,
                Registro2Args.fromBundle(requireArguments()).address,
                OrionApi.USER_ENABLED
            )

            usuarioViewModel.registrarUsuario(user, Registro2Args.fromBundle(requireArguments()).password, imgUsuario)

            usuarioViewModel.usuarioRegistadoOk.observe(viewLifecycleOwner, Observer {
                //Cuando el registro falla al primer intento, cuando un segundo intento legal se ejecuta, se observa
                // nuevamente que el Ok está en falso, lo que no permite la correcta circulación en la aplicación.
                if (usuarioViewModel.usuarioRegistadoOk.value == true){
                    val action = Registro2Directions.actionRegistro2ToRegistro3()
                    v.findNavController().navigate(action)
                } else {
                    Log.d("testRegistro", usuarioViewModel.error)
                    Snackbar.make(v, usuarioViewModel.error, Snackbar.LENGTH_SHORT).show()
                    v.findNavController().navigate(R.id.action_registro2_to_registro1)
                }
            })
        }
    }

}
package com.dTeam.ciudadanos.fragments
import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.view.allViews

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.dTeam.ciudadanos.R
import com.dTeam.ciudadanos.entities.Reclamo
import com.dTeam.ciudadanos.viewmodels.CategoriaViewModel
import com.dTeam.ciudadanos.viewmodels.ReclamoViewModel
import com.dTeam.ciudadanos.viewmodels.UsuarioViewModel
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar

import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import java.util.*
import kotlin.collections.ArrayList


class FragmentNuevoReclamo:Fragment() {
    lateinit var v: View
    lateinit var btnGenerarReclamo : Button
    lateinit var btnCargarImgs : Button
    lateinit var txtDescripcion : EditText
    lateinit var txtDireccion : EditText
    private lateinit var reclamoViewModel: ReclamoViewModel
    private lateinit var usuarioViewModel: UsuarioViewModel
    private lateinit var categoriaViewModel: CategoriaViewModel
    private lateinit var lblCategoriaReclamo : TextView
    private lateinit var lblSubcategoriaReclamo : TextView
    private lateinit var lblDireccion : TextView
    private lateinit var lblDescripcion : TextView
    private lateinit var imgReclamo : ImageView
    private lateinit var lbltipoReclamoNuevoReclamo : TextView
    private lateinit var lblSubtipoReclamoNuevoReclamo : TextView
    private var listaImgs : List<Uri> = listOf()
    val PERMISSION_ID = 42
    val ID_CONTRAINT_LAYOUT = -1
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var  geocoder : Geocoder
    lateinit var addresses : List<Address>
    private lateinit var progresBar: ProgressBar

    private var pedirDosVeceslaUbicacion = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v =  inflater.inflate(R.layout.nuevo_reclamo, container, false)
        geocoder = Geocoder(requireContext(), Locale.getDefault())
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        txtDescripcion=v.findViewById(R.id.txtDescripcion)
        txtDireccion=v.findViewById(R.id.txtDireccion)
        lblDireccion=v.findViewById(R.id.lblDireccNuevoReclamo)
        lblDescripcion=v.findViewById(R.id.lblDescNuevoReclamo)
        btnGenerarReclamo = v.findViewById(R.id.btnGenerarReclamo)
        btnCargarImgs = v.findViewById(R.id.btnSubirImgsReclamo)
        progresBar = v.findViewById(R.id.progressBarNuevoReclamo)
        lbltipoReclamoNuevoReclamo = v.findViewById(R.id.lbltipoReclamoNuevoReclamo)
        lblSubtipoReclamoNuevoReclamo = v.findViewById(R.id.lblSubtipoReclamoNuevoReclamo)
        btnGenerarReclamo.setOnClickListener{
            if (validarCampos(txtDescripcion, txtDireccion)){
               val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmar reclamo")
                builder.setMessage("¿Desea confirmar el reclamo?")
                builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
                    var reclamo = Reclamo(
                        reclamoViewModel.getCategoria()!!,
                        reclamoViewModel.getSubcategoria()!!,
                        txtDireccion.text.toString(),
                        txtDescripcion.text.toString(),
                        usuarioViewModel.obtenerUsuarioLogueado()!!.uid,
                        "Abierto"
                    )
                    progresBar.visibility = View.VISIBLE
                    visibilidadElementos(View.INVISIBLE, progresBar.id)
                    reclamoViewModel.generarReclamo(reclamo, listaImgs)


                    reclamoViewModel.reclamoGeneradoOk.observe(viewLifecycleOwner, Observer { list ->
                        if (reclamoViewModel.reclamoGeneradoOk.value == true){
                            progresBar.visibility = View.INVISIBLE
                            val action = FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToExitoReclamo()
                            v.findNavController().navigate(action)
                        }
                        else{
                            progresBar.visibility = View.INVISIBLE
                            visibilidadElementos(View.VISIBLE, progresBar.id)
                            Snackbar.make(v,getString(R.string.errorGeneral), Snackbar.LENGTH_SHORT).show()

                        }
                    })
                }
                builder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

                }
                builder.show()

            }
        }

        btnCargarImgs.setOnClickListener{

            TedImagePicker.with(requireContext())
                .title("Seleccione Imágenes")
                .max(5,"No puede subir más de 5 imágenes" )
                .buttonText("Listo")
                .mediaType(MediaType.IMAGE)
                .startMultiImage { uriList -> listaImgs = uriList }

        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Descartar reclamo")
                builder.setMessage("¿Desea descartar el reclamo?")
                builder.setPositiveButton("Si") { dialogInterface: DialogInterface, i: Int ->
                    val action = FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToInicioCiudadano()
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

    fun validarCampos(vararg campos:EditText):Boolean{
        var camposValidos = true
        for (campo in campos) {
            if(campo.text.isEmpty()){
                camposValidos = false
                campo.setError(getString(R.string.campoVacio))
            }
        }
        return camposValidos
    }

    override fun onResume() {
        super.onResume()
        progresBar.visibility = View.INVISIBLE
    }
    override fun onStart() {
        super.onStart()
        lblCategoriaReclamo = v.findViewById(R.id.lbltipoReclamoNuevoReclamo)
        lblSubcategoriaReclamo = v.findViewById(R.id.lblSubtipoReclamoNuevoReclamo)
        imgReclamo = v.findViewById(R.id.imgReclamo)
        categoriaViewModel.getImgCategoria(reclamoViewModel.getCategoria()!!)

        categoriaViewModel.imgCategoria.observe(viewLifecycleOwner, Observer { list ->
            val imgReclamo = categoriaViewModel.imgCategoria.value
            var imgCategoriaReclamo : ImageView =  v.findViewById(R.id.imgReclamo)
            Glide.with(this)
                .load(imgReclamo)
                .into(imgCategoriaReclamo)
        })



        lblCategoriaReclamo.text = reclamoViewModel.getCategoria()
        lblSubcategoriaReclamo.text = reclamoViewModel.getSubcategoria()
        if (pedirDosVeceslaUbicacion) {
            getLastLocation()
            pedirDosVeceslaUbicacion = false
        } else if (isLocationEnabled()) {
            getLastLocation()
        }


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reclamoViewModel = ViewModelProvider(requireActivity()).get(ReclamoViewModel::class.java)
        usuarioViewModel = ViewModelProvider(requireActivity()).get(UsuarioViewModel::class.java)
        categoriaViewModel = ViewModelProvider(requireActivity()).get(CategoriaViewModel::class.java)
        if(usuarioViewModel.obtenerUsuarioLogueado()==null){
            var action = FragmentNuevoReclamoDirections.actionFragmentNuevoReclamoToLogIn()
            v.findNavController().navigate(action)
        }
    }

    private fun visibilidadElementos(vis:Int, vararg views:Int) {
        //Las views a excluir la pasamos a una mutableList y le agregamos el ContraintLayout para que nunca lo modifique
        val viewsFueraDeAlcance = views.toMutableList()
        viewsFueraDeAlcance.add(ID_CONTRAINT_LAYOUT)

        //Filtramos todas las vistas que no sean ni el contraint layout, ni las recibidas por parámetro
        val allViews = v.allViews.filter { vista -> !viewsFueraDeAlcance.contains(vista.id) }

        //Ejecutamos el View. GONE, VISIBLE o INVISIBLE que haya llegado en "vis"
        for (e: View in allViews) {
            e.visibility = vis
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        txtDireccion.setText(addresses.get(0).getAddressLine(0))
                        Log.d ("Test",location.latitude.toString())
                        Log.d ("Test",location.longitude.toString())
                        //findViewById<TextView>(R.id.latTextView).text = location.latitude.toString()
                        //findViewById<TextView>(R.id.lonTextView).text = location.longitude.toString()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, encienda la ubicación", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 60000
        mLocationRequest.fastestInterval = 0

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Log.d ("Test",mLastLocation.latitude.toString())
            Log.d ("Test",mLastLocation.longitude.toString())
//            findViewById<TextView>(R.id.latTextView).text = mLastLocation.latitude.toString()
//            findViewById<TextView>(R.id.lonTextView).text = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

}


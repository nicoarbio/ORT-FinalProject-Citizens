package com.dTeam.ciudadanos.viewmodels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Usuario
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import com.google.firebase.auth.FirebaseAuth

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()

    private var  auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
    }

    fun registrarUsuario(usuario: Usuario): Boolean {
        //TODO: Guardar el resto de los datos en FiWare
        var guardadoOk = true
        viewModelScope.launch {
            try {
                auth!!.createUserWithEmailAndPassword(usuario.email, usuario.contrasenia)
                    .await()
            }catch (e : Exception){
                guardadoOk = false
                Log.d("Test", "Error al generar usuario: ", e)
            }
        }
        return guardadoOk
    }
}
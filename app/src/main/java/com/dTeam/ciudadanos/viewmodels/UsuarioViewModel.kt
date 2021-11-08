package com.dTeam.ciudadanos.viewmodels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.network.OrionApi
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()
    var usuarioRegistadoOk = MutableLiveData<Boolean>()
    var usuarioLogueadoOk = MutableLiveData<Boolean>()
    var error = String()

    private var  auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
        //getUsuarios()
    }

    fun registrarUsuario(usuario: Usuario) { //TODO: Guardar el resto de los datos en FiWare
        viewModelScope.launch {
            try {
                auth!!.createUserWithEmailAndPassword(usuario.email, usuario.contrasenia)
                    .await()
                usuarioRegistadoOk.value = true
            }catch (e : FirebaseAuthWeakPasswordException){
                error = "La contraseña debe tener al menos 6 caracteres"
                usuarioRegistadoOk.value = false
            }catch (e : FirebaseAuthInvalidCredentialsException){
                error = "El mail ingresado debe tener un formato válido"
                usuarioRegistadoOk.value = false
            }catch (e : FirebaseAuthUserCollisionException){
                error = "El mail ingresado ya se encuentra registrado"
                usuarioRegistadoOk.value = false
            }catch (e : Exception){
                error = "Ocurrió un error al registrar el usuario. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
            }
        }
    }

    fun obtenerUsuarioLogueado(): FirebaseUser? {
        return auth?.currentUser
    }

    fun iniciarSesion(mail:String, password:String){
        viewModelScope.launch {
            try {
                auth!!.signInWithEmailAndPassword(mail, password)
                    .await()
                usuarioLogueadoOk.value = true
            }catch(e : Exception){
                error = "Usuario y/o contraseña incorrectos"
                usuarioLogueadoOk.value = false
            }
        }
    }

    fun cerrarSesion(){
        auth?.signOut()
    }

    private fun getUsuarios() {
        viewModelScope.launch {
            try {
                val listResult = OrionApi.retrofitService.getUsuarios()

            } catch (e: Exception) {

            }
        }
    }

    fun getEmail(): String? {
        return usuario.value?.email
    }
    fun getNombre(): String? {
        return usuario.value?.nombre
    }
    fun getApellido(): String? {
        return usuario.value?.apellido
    }
    fun getDireccion(): String? {
        return usuario.value?.direccion
    }
    fun getTelefono(): String? {
        return usuario.value?.telefono
    }
    fun getDni(): String? {
        return usuario.value?.dni
    }

}
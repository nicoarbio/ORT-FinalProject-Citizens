package com.dTeam.ciudadanos.viewmodels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.SingleLiveEvent
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.network.OrionApi
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()
    var usuarioRegistadoOk = SingleLiveEvent<Boolean>()
    var usuarioLogueadoOk = MutableLiveData<Boolean>()
    var usuariosResponsables = MutableLiveData<MutableList<Usuario>>()
    var usuarios = MutableLiveData<MutableList<Usuario>>()
    var error = String()

    private var  auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
    }

    //fun registrarUsuario(email : String, pwd : String, adr : String) {
    fun registrarUsuario(user: Usuario, pwd : String) {
        viewModelScope.launch {
            try {
                auth!!.createUserWithEmailAndPassword(user.email, pwd).await()

                user.documentId = auth!!.uid!!
                usuario.value = user

                Log.d("ORION_API", usuario.value.toString())
                registrarUsuarioOrion()
                Log.d("ORION_API", "Registrado correctamente")

                usuarioRegistadoOk.value = true
            }catch (e : FirebaseAuthWeakPasswordException){
                error = "La contraseña debe tener al menos 6 caracteres"
                usuarioRegistadoOk.value = false
            }catch (e : FirebaseAuthInvalidCredentialsException){
                error = "El mail ingresado debe tener un formato válido"
                usuarioRegistadoOk.value = false
            }catch (e : FirebaseAuthUserCollisionException) {
                error = "El mail ingresado ya se encuentra registrado"
                usuarioRegistadoOk.value = false
            }catch (e : Exception){
                error = "Ocurrió un error al registrar el usuario. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
                Log.d("ORION_API", e.toString())
            }
        }
    }

    fun obtenerUsuarioLogueado(): FirebaseUser? {
        return auth?.currentUser
    }

    fun actualizarUsuarioLogueado() {
        getUsuarioByUID(obtenerUsuarioLogueado()!!.uid)
    }

    fun iniciarSesion(mail:String, password:String){
        viewModelScope.launch {
            try {
                auth!!.signInWithEmailAndPassword(mail, password).await()
                usuarioLogueadoOk.value = true
            } catch(e : Exception) {
                error = "Usuario y/o contraseña incorrectos"
                usuarioLogueadoOk.value = false
            }
        }
    }

    fun cerrarSesion(){
        auth?.signOut()
    }

    // Metodos que utilizan la API de ORION

    suspend fun registrarUsuarioOrion() {
        OrionApi.retrofitService.registrarUsuario(usuario.value!!)
    }

    fun getUsuarioByUID(UID : String) {
        viewModelScope.launch {
            try {
                usuario.value = OrionApi.retrofitService.getUsuarioByUID(UID)
            } catch (e: Exception) {
                Log.d("ORION_API_errorGetUsrUID", e.toString())
            }
        }
    }

    // getUsuariosResponsables -> lista de usuarios cuyo rol sea responsable
    fun getUsuariosResponsables() {
        viewModelScope.launch {
            try {
                usuariosResponsables.value = OrionApi.retrofitService.getUsuariosResponsables().toMutableList()
            } catch (e: Exception) {
                Log.d("ORION_API", e.toString())
            }
        }
    }


    //TODO: metodo que permita consultar el rol del usuario a partir de un UID
    /*private fun getRolByUID(UID : String) {
        try {
            //usuario.value = getUsuarioByUID(UID)
        } catch (e: Exception) {
            Log.d("ORION_API", e.toString())
        }
    }*/

    //TODO: metodo que permita obtener una lista con todos los usuarios
    fun getUsuarios() {
        viewModelScope.launch {
            try {
                usuarios.value = OrionApi.retrofitService.getUsuarios().toMutableList()
            } catch (e: Exception) {
                Log.d("ORION_API", e.toString())
            }
        }
    }

    //Getters sobre MutableLiveData Usuario

    fun esResponsable():Boolean {
        return usuario.value?.rol.equals("Responsable")
    }
    fun esCiudaddano():Boolean {
        return usuario.value?.rol.equals("Ciudaddano")
    }
    fun esMunicipio():Boolean {
        return usuario.value?.rol.equals("Municipio")
    }

    fun getEmail(): String? {
        return obtenerUsuarioLogueado()!!.email
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
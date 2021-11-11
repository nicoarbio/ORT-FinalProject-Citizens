package com.dTeam.ciudadanos.viewmodels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.network.OrionApi
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()
    var usuarioRegistadoOk = MutableLiveData<Boolean>()
    var usuarioLogueadoOk = MutableLiveData<Boolean>()
    var error = String()

    private var  auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
    }

    //fun registrarUsuario(user: Usuario) {
    fun registrarUsuario(email : String, pwd : String, adr : String) {
        viewModelScope.launch {
            try {
                auth!!.createUserWithEmailAndPassword(email, pwd).await()

                usuario.value = Usuario()
                usuario.value?.documentId = auth!!.uid!!
                usuario.value?.type = "Usuario"
                usuario.value?.rol = "Ciudadano"
                usuario.value?.email = email
                usuario.value?.direccion = adr

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
            }
            //kotlin (a diferencia de java) no soporta multiple-catching..
            catch (e: SocketTimeoutException) {
                //Este error es mostrado en caso de pasar los 10 segundos sin respuesta del servidor
                error = "Ocurrió un error en la comunicación. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
                Log.d("ORION_API", e.toString())
            } catch(e: HttpException) {
                //Este error es mostrado en caso de estar armando mal la petición
                error = "Ocurrió un error en la comunicación. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
                Log.d("ORION_API", e.toString())
            }catch (e : Exception){
                error = "Ocurrió un error al registrar el usuario. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
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

    private suspend fun registrarUsuarioOrion() {
        OrionApi.retrofitService.registrarUsuario(usuario.value!!)
    }

    private fun getUsuarioByUID(UID : String) {
        viewModelScope.launch {
            try {
                usuario.value = OrionApi.retrofitService.getUsuarioByUID(UID)
            } catch (e: Exception) {
                Log.d("ORION_API", e.toString())
            }
        }
    }

    //TODO: metodo que permita consultar el rol del usuario a partir de un UID
    private fun getRolByUID(UID : String) {
        try {
            //usuario.value.rol = getUsuarioByUID(UID).rol
        } catch (e: Exception) {
            Log.d("ORION_API", e.toString())
        }
    }

    //TODO: metodo que permita obtener una lista con todos los usuarios
    private fun getUsuarios() { //: MutableList<Usuario> {
        viewModelScope.launch {
            try {
                //usuarios = OrionApi.retrofitService.getUsuarios().await().toMutableList()
            } catch (e: Exception) {
                Log.d("ORION_API", e.toString())
            }
        }
    }


    //Getters sobre MutableLiveData Usuario

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
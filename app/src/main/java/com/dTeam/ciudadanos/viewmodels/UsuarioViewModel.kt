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
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()
    var usuarioRegistadoOk = SingleLiveEvent<Boolean>()
    var usuarioLogueadoOk = SingleLiveEvent<Boolean>()
    var usuariosResponsables = MutableLiveData<MutableList<Usuario>>()
    var usuarios = MutableLiveData<MutableList<Usuario>>()
    var error = String()

    private var  auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
    }

    fun registrarUsuario(user: Usuario, pwd : String) {
        viewModelScope.launch {
            var flagOrion = false
            var uidAuxiliar = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
            try {
                //autogeneramos un valor de uid que no se repetirá para probar la conexion a orion
                // ya que se puede borrar un usuario de orion pero no de Firebase
                user.documentId = uidAuxiliar
                OrionApi.retrofitService.registrarUsuario(user)
                auth!!.createUserWithEmailAndPassword(user.email, pwd).await()
                user.documentId = auth!!.uid!!
                OrionApi.retrofitService.eliminarUsuario(uidAuxiliar)
                OrionApi.retrofitService.registrarUsuario(user)
                usuario.value = user
                Log.d("ORION_API", "Usuario registrado correctamente: " + usuario.value.toString())
                usuarioRegistadoOk.value = true
            }catch (e : FirebaseAuthWeakPasswordException){
                error = "La contraseña debe tener al menos 6 caracteres"
                usuarioRegistadoOk.value = false
                flagOrion = true
            }catch (e : FirebaseAuthInvalidCredentialsException){
                error = "El mail ingresado debe tener un formato válido"
                usuarioRegistadoOk.value = false
                flagOrion = true
            }catch (e : FirebaseAuthUserCollisionException) {
                error = "El mail ingresado ya se encuentra registrado"
                usuarioRegistadoOk.value = false
                flagOrion = true
            }catch (e : Exception){
                error = "Ocurrió un error al registrar el usuario. Vuelva a intentar más tarde"
                usuarioRegistadoOk.value = false
                flagOrion = true
                Log.d("ORION_API", e.toString())
            }finally {
                if (flagOrion) {
                    try {
                        OrionApi.retrofitService.eliminarUsuario(uidAuxiliar)
                    } catch (e: Exception) {
                        Log.d("ORION_API", e.toString())
                    }
                }
            }
        }
    }

    fun cerrarSesion(){
        auth?.signOut()
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
                var posibleCiudadano = getUsuarioByEmail(mail)

                if (posibleCiudadano != null && posibleCiudadano.rol == "Ciudadano") {
                    auth!!.signInWithEmailAndPassword(mail, password)
                        .await()
                    usuario.value = OrionApi.retrofitService.getUsuarioByUID(auth!!.uid!!)
                } else {
                    throw Exception("Como si el usuario no existiera. Parametros incorrectos")
                }
                usuarioLogueadoOk.value = true
            } catch(e : Exception) {
                error = "Usuario y/o contraseña incorrectos"
                usuarioLogueadoOk.value = false
            }
        }
    }

    suspend fun getUsuarioByEmail(email:String) : Usuario? {
        try {
            val listaAux = OrionApi.retrofitService.getUsuarioByEmail("email:"+email)
            return listaAux.find {
                    usr -> usr.email == email
            }
        }catch (e:Exception) {
            Log.d("ORION_API", e.toString())
            return null
        }
    }


    // Metodos que utilizan la API de ORION


    fun getUsuarioByUID(UID : String) {
        viewModelScope.launch {
            try {
                usuario.value = OrionApi.retrofitService.getUsuarioByUID(UID)
            } catch (e: Exception) {
                Log.d("ORION_API_errorGetUsrUID: ", UID + ": " + e.toString())
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
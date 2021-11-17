package com.dTeam.ciudadanos.viewmodels
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dTeam.ciudadanos.SingleLiveEvent
import com.dTeam.ciudadanos.entities.Usuario
import com.dTeam.ciudadanos.network.OrionApi
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UsuarioViewModel : ViewModel() {

    var usuario = MutableLiveData<Usuario>()
    var usuarioRegistadoOk = SingleLiveEvent<Boolean>()
    var usuarioLogueadoOk = SingleLiveEvent<Boolean>()
    var error = String()

    private var  auth: FirebaseAuth? = null
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    init {
        auth = FirebaseAuth.getInstance()
        usuario.value = Usuario()
    }

    fun registrarUsuario(user: Usuario, pwd : String, img : Uri) {
        viewModelScope.launch {

            try {
                OrionApi.retrofitService.verificarConexion()
                auth!!.createUserWithEmailAndPassword(user.email, pwd).await()
                user.documentId = auth!!.uid!!
                if(!Uri.EMPTY.equals(img)){
                    var uploadTask: UploadTask
                    val imgReclamo = storageRef.child("usuarios/${img.lastPathSegment}")
                    uploadTask = imgReclamo.putFile(img)
                    uploadTask.await()
                    if (uploadTask.isSuccessful()) {
                        var url = storageRef.child("usuarios/${img.lastPathSegment}").downloadUrl.await()
                        user.fotoPerfil = url.toString().replace('=', '*') //ORION NO PERMITE GUARDAR /
                    }
                }

                Log.d("usuarioViewModel", user.toString())
                OrionApi.retrofitService.registrarUsuario(user)
                usuario.value = user
                Log.d("ORION_API", "Usuario registrado correctamente: " + usuario.value.toString())
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
                var posibleCiudadano = getUsuarioByQuery(mail)
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

    suspend fun getUsuarioByQuery(email:String) : Usuario? {
        try {
            val listaAux = OrionApi.retrofitService.getUsuarioByQuery("isEnabled:"+OrionApi.USER_ENABLED+";email:"+email)
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
}
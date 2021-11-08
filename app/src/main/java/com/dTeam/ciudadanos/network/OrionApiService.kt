package com.dTeam.ciudadanos.network

import com.dTeam.ciudadanos.entities.Usuario
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "http://localhost:1026/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OrionApiService {

    @GET("entities?type=User&options=keyValues")
    suspend fun getUsuarios(): List<Usuario>

    @GET("entities")
    suspend fun getUsuarioByUID(@Query("UID") UID: String): Usuario

    @POST("entities")
    suspend fun registrarUsuario(@Body usuario: Usuario)

}

object OrionApi {
    val retrofitService : OrionApiService by lazy {
        retrofit.create(OrionApiService::class.java)
    }

}
package com.dTeam.ciudadanos.repositories

import com.dTeam.ciudadanos.entities.Usuario

class UsuarioRepository {

    private var usuariosList : MutableList<Usuario> = mutableListOf()

    init {
    }

    fun getUsuarios() : MutableList<Usuario>{

        return usuariosList
    }

}
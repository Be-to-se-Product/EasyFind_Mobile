package com.easy.myapplication.dto

import com.easy.myapplication.utils.TipoUsuario

data class UsuarioTokenDTO(
    val id :Long? = null,
    val email: String? = null,
    val nome: String? = null,
    val token:String? = null,
    val tipoUsuario: TipoUsuario? = null,
)

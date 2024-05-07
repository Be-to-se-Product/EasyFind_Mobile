package com.easy.myapplication.dto

data class ConsumidorCriacaoDTO(
    val nome: String? = null,
    val cpf: String? = null,
    val celular: String? = null,
    val genero: String? = null,
    val dataNascimento: String? = null,
    val usuario: UsuarioCriacaoDTO? = null,
)
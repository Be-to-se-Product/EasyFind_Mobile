package com.easy.myapplication.dto

data class Endereco (
    val latitude: Double? = null,
    val longitude: Double? = null,
    private val numero: String? = null,
    private val rua: String? = null,
    private val bairro: String? = null
)
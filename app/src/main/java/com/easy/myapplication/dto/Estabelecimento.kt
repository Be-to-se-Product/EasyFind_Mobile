package com.easy.myapplication.dto

import java.time.LocalDate



data class Estabelecimento (
    val id: Long? = null,
    val nome: String? = null,
    val segmento: String? = null,
    val dataCriacao: String? = null,
    val endereco: Endereco? = null,
    val agenda: List<Agenda>? = null,
    val metodoPagamento: List<MetodoPagamento>? = null,
    val telefone: String? = null,
    val site: String? = null,
    val tempoCarro: Long? = null,
    val tempoPessoa: Long? = null,
    val tempoBike: Long? = null
)
package com.easy.myapplication.dto

import java.sql.Time



data class Estabelecimento (
    val id: Long? = null,
    val nome: String? = null,
    val segmento: String? = null,
    val dataCriacao: String? = null,
    val endereco: Endereco? = null,
    val imagem:String?=null,
    val agenda: List<Agenda>? = null,
    val metodoPagamento: List<MetodoPagamento>? = null,
    val telefone: String? = null,
    val site: String? = null,
    val tempoCarro: Double? = null,
    val tempoPessoa: Double? = null,
    val tempoBike: Double? = null
)
package com.easy.myapplication.dto

import com.google.gson.annotations.SerializedName


data class Produto (
    val id: Long? = null,
    val nome: String? = null,
    val categoria: String? = null,
    val descricao: String? = null,
    val precoAtual: Double? = null,
    val precoAntigo: Double? = null,
    val imagens: List<String>? = null,
    @SerializedName("avaliacao")
    val avaliacao: List<Avaliacao>? = null,
    val mediaAvaliacao: Double? = null,
    val estabelecimento: Estabelecimento? = null
)